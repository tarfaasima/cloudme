package org.cloudme.loclist.item;

import static org.cloudme.gaestripes.BaseDao.filter;
import static org.cloudme.gaestripes.BaseDao.orderBy;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cloudme.gaestripes.QueryOperator;
import org.cloudme.loclist.dao.CheckinDao;
import org.cloudme.loclist.dao.ItemDao;
import org.cloudme.loclist.dao.ItemInstanceDao;
import org.cloudme.loclist.dao.ItemListDao;
import org.cloudme.loclist.dao.ItemOrderDao;
import org.cloudme.loclist.dao.TickDao;
import org.cloudme.loclist.dao.UpdateDao;
import org.cloudme.loclist.model.Checkin;
import org.cloudme.loclist.model.Item;
import org.cloudme.loclist.model.ItemInstance;
import org.cloudme.loclist.model.ItemList;
import org.cloudme.loclist.model.ItemOrder;
import org.cloudme.loclist.model.Tick;
import org.cloudme.loclist.model.Update;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.repackaged.com.google.common.base.StringUtil;
import com.google.inject.Inject;

public class ItemService {
    private static final Log LOG = LogFactory.getLog(ItemService.class);
    @Inject
    private ItemDao itemDao;
    @Inject
    private ItemListDao itemListDao;
    @Inject
    private ItemInstanceDao itemInstanceDao;
    @Inject
    private TickDao tickDao;
    @Inject
    private CheckinDao checkinDao;
    @Inject
    private ItemOrderDao itemOrderDao;
    @Inject
    private UpdateDao updateDao;

    /**
     * Creates a new {@link Item} and adds it to the {@link ItemList} with the
     * given id. If another {@link Item} with the same text already exists, the
     * {@link Item} will not be created, but added to the {@link ItemList}. If
     * the {@link Item} is already added to the {@link ItemList} it will not be
     * added again.
     * 
     * @param itemListId
     *            The id of the {@link ItemList}.
     * @param item
     *            The {@link Item}.
     * @param attribute
     *            TODO
     */
    public void createItem(Long itemListId, Item item, String attribute) {
        Item existingItem = itemDao.findSingle("text", item.getText());
        if (existingItem != null) {
            item.setId(existingItem.getId());
            item.setText(existingItem.getText());
        }
        else {
            itemDao.save(item);
        }
        addOrRemove(itemListId, item.getId(), attribute);
    }

    public void put(ItemList itemList) {
        itemListDao.save(itemList);
    }

    public void tick(Long checkinId, Long itemInstanceId) {
        ItemInstance itemInstance = itemInstanceDao.find(itemInstanceId);
        itemInstance.setTicked(!itemInstance.isTicked());
        itemInstanceDao.save(itemInstance);
        Long itemId = itemInstance.getItemId();
        if (itemInstance.isTicked()) {
            Tick tick = new Tick();
            tick.setCheckinId(checkinId);
            tick.setItemId(itemId);
            tick.setTimestamp(System.currentTimeMillis());
            tickDao.save(tick);
        }
        else {
            tickDao.deleteByCheckinAndItem(checkinId, itemId);
        }
    }

    public List<ItemList> getItemLists() {
        return itemListDao.listAll(orderBy("name"));
    }

    public List<ItemInstance> getItemInstances(Long itemListId) {
        return itemInstanceDao.listByItemList(itemListId);
    }

    public void orderByCheckin(Long checkinId, List<ItemInstance> itemInstances) {
        Checkin checkin = checkinDao.find(checkinId);
        if (checkin != null) {
            Iterable<ItemOrder> itemOrders = itemOrderDao.findByLocation(checkin.getLocationId());
            final Map<Long, ItemOrder> itemOrderMap = new HashMap<Long, ItemOrder>();
            for (ItemOrder itemOrder : itemOrders) {
                itemOrderMap.put(itemOrder.getItemId(), itemOrder);
            }
            Collections.sort(itemInstances, new ItemInstanceComparator(itemOrderMap));
        }
    }

    public Collection<ItemInstance> getAllItemInstances(Long itemListId) {
        SortedSet<ItemInstance> itemInstances = new TreeSet<ItemInstance>(getItemInstances(itemListId));
        Set<Long> itemIds = new HashSet<Long>(itemInstances.size());
        for (ItemInstance itemInstance : itemInstances) {
            itemIds.add(itemInstance.getItemId());
            itemInstance.setInList(true);
        }
        for (Item item : itemDao.findAll(orderBy("text"))) {
            if (!itemIds.contains(item.getId())) {
                ItemInstance itemInstance = new ItemInstance();
                itemInstance.setItemId(item.getId());
                itemInstance.setText(item.getText());
                itemInstances.add(itemInstance);
            }
        }
        return itemInstances;
    }

    public void updateItemOrder() {
        Update currentUpdate = new Update();
        currentUpdate.setTimestamp(System.currentTimeMillis());
        NamespaceManager.set("");
        Iterator<Update> updates = updateDao.findAll(orderBy("-timestamp")).iterator();
        long lastTimestamp = updates.hasNext() ? updates.next().getTimestamp() : 0;
        Iterator<Checkin> checkins = checkinDao.findAll(orderBy("-timestamp")).iterator();
        while (checkins.hasNext()) {
            Checkin checkin = checkins.next();
            if (checkin.getTimestamp() < lastTimestamp) {
                break;
            }
            Long locationId = checkin.getLocationId();
            Iterable<Tick> ticks = tickDao.findByCheckin(checkin.getId());
            Iterable<ItemOrder> itemOrders = itemOrderDao.findByLocation(locationId);
            Map<Long, ItemOrder> itemOrderMap = new HashMap<Long, ItemOrder>();
            for (ItemOrder itemOrder : itemOrders) {
                itemOrderMap.put(itemOrder.getId(), itemOrder);
            }
            itemOrders = new ItemOrderEngine().createOrder(locationId, ticks, itemOrderMap);
            itemOrderDao.save(itemOrders);
        }
        updateDao.save(currentUpdate);
    }

    public ItemList getItemList(Long id) {
        return itemListDao.find(id);
    }

    /**
     * Deletes the {@link ItemList} and all {@link ItemInstance}s.
     * 
     * @param id
     *            The id of the {@link ItemList}
     */
    public void deleteItemList(Long id) {
        itemListDao.delete(id);
        itemInstanceDao.deleteAll(filter("itemListId", id));
    }

    /**
     * Deletes the {@link Item}, the {@link ItemInstance}, the {@link ItemOrder}
     * and the {@link Tick}.
     * 
     * @param id
     *            The id of the {@link Item}.
     */
    public void deleteItem(Long id) {
        itemDao.delete(id);
        QueryOperator filter = filter("itemId", id);
        itemInstanceDao.deleteAll(filter);
        itemOrderDao.deleteAll(filter);
        tickDao.deleteAll(filter);
    }

    public void addOrRemove(Long itemListId, Long itemId, String attribute) {
        ItemInstance itemInstance = new ItemInstance();
        Item item = itemDao.find(itemId);
        Iterator<ItemInstance> it = itemInstanceDao.findAll(filter("itemListId", itemListId), filter("itemId", itemId))
                .iterator();
        if (it.hasNext()) {
            itemInstance = it.next();
            if (it.hasNext()) {
                throw new IllegalStateException(String.format("Multiple instances exist for item %s in list %s",
                        item,
                        itemListDao.find(itemListId)));
            }
            if (StringUtil.isEmptyOrWhitespace(attribute)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Deleting itemInstance with itemListId = " + itemListId + " and itemId = " + itemId);
                }
                itemInstanceDao.deleteAll(filter("itemListId", itemListId), filter("itemId", itemId));
                return;
            }
        }
        else {
            itemInstance.setItemId(itemId);
            itemInstance.setItemListId(itemListId);
        }
        itemInstance.setAttribute(attribute);
        itemInstance.setText(item.getText());
        itemInstanceDao.save(itemInstance);
    }
}
