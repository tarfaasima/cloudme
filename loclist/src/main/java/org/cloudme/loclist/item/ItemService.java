package org.cloudme.loclist.item;

import static org.cloudme.gaestripes.BaseDao.filter;
import static org.cloudme.gaestripes.BaseDao.orderBy;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.google.inject.Inject;

public class ItemService {
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

    public void put(Item item) {
        itemDao.save(item);
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

    public List<ItemInstance> getItemInstancesInItemList(Long checkinId, Long itemListId) {
        List<ItemInstance> itemInstances = itemInstanceDao.listByItemList(itemListId);
        Checkin checkin = checkinDao.find(checkinId);
        if (checkin != null) {
            Iterable<ItemOrder> itemOrders = itemOrderDao.findByLocation(checkin.getLocationId());
            final Map<Long, ItemOrder> itemOrderMap = new HashMap<Long, ItemOrder>();
            for (ItemOrder itemOrder : itemOrders) {
                itemOrderMap.put(itemOrder.getItemId(), itemOrder);
            }
            Collections.sort(itemInstances, new ItemInstanceComparator(itemOrderMap));
        }
        return itemInstances;
    }

    public void updateItemOrder() {
        Update currentUpdate = new Update();
        currentUpdate.setTimestamp(System.currentTimeMillis());
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

    public Items getItems(Long itemListId) {
        Items items = new Items();
        Iterable<ItemInstance> itemInstances = itemInstanceDao.findByItemList(itemListId);
        Set<Long> itemIdsInList = new HashSet<Long>();
        for (ItemInstance itemInstance : itemInstances) {
            itemIdsInList.add(itemInstance.getItemId());
        }
        for (Item item : itemDao.findAll(orderBy("text"))) {
            if (itemIdsInList.contains(item.getId())) {
                items.addItemInList(item);
            }
            else {
                items.addItemNotInList(item);
            }
        }
        return items;
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

    public void addToItemList(Long itemListId, Long itemId) {
        Item item = itemDao.find(itemId);
        ItemInstance itemInstance = new ItemInstance();
        itemInstance.setItemId(itemId);
        itemInstance.setItemListId(itemListId);
        itemInstance.setText(item.getText());
        itemInstanceDao.save(itemInstance);
    }
}
