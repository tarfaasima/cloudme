package org.cloudme.loclist.item;

import static org.cloudme.gaestripes.BaseDao.orderBy;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    public void put(ItemInstance itemInstance) {
        itemInstanceDao.save(itemInstance);
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

    public List<ItemInstance> getItemInstances(Long checkinId, Long itemListId) {
        List<ItemInstance> itemInstances = itemInstanceDao.listByItemList(itemListId);
        Checkin checkin = checkinDao.find(checkinId);
        if (checkin != null) {
            Iterable<ItemOrder> itemOrders = itemOrderDao.findByLocation(checkin.getLocationId());
            final Map<Long, ItemOrder> itemOrderMap = new HashMap<Long, ItemOrder>();
            for (ItemOrder itemOrder : itemOrders) {
                itemOrderMap.put(itemOrder.getItemId(), itemOrder);
            }
            Collections.sort(itemInstances, new Comparator<ItemInstance>() {
                @Override
                public int compare(ItemInstance i1, ItemInstance i2) {
                    ItemOrder o1 = itemOrderMap.get(i1.getItemId());
                    ItemOrder o2 = itemOrderMap.get(i2.getItemId());
                    return o1 == null ? o2 == null ? 0 : 1 : o2 == null ? -1 : o1.getIndex() - o2.getIndex();
                }
            });
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
}
