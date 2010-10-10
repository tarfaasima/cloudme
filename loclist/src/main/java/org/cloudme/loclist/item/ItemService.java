package org.cloudme.loclist.item;

import static org.cloudme.gaestripes.BaseDao.filter;
import static org.cloudme.gaestripes.BaseDao.orderBy;

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
import org.cloudme.loclist.model.Checkin;
import org.cloudme.loclist.model.Item;
import org.cloudme.loclist.model.ItemInstance;
import org.cloudme.loclist.model.ItemList;
import org.cloudme.loclist.model.ItemOrder;
import org.cloudme.loclist.model.Tick;

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

    public void put(Item item) {
        itemDao.save(item);
    }

    public void put(ItemList itemList) {
        itemListDao.save(itemList);
    }

    public void put(ItemInstance itemInstance) {
        itemInstanceDao.save(itemInstance);
    }

    public void tick(Long locationId, Long itemInstanceId) {
        Tick tick = new Tick();
        tick.setLocationId(locationId);
        tick.setItemInstanceId(itemInstanceId);
        // TODO Loading the itemId here is probably a bad idea; performance test
        // required. Check if caching works sufficiently.
        Long itemId = itemInstanceDao.find(itemInstanceId).getItemId();
        tick.setItemId(itemId);
        tick.setTimestamp(System.currentTimeMillis());
        tickDao.save(tick);
    }

    public List<ItemList> getItemLists() {
        return itemListDao.listAll(orderBy("name"));
    }

    public List<ItemInstance> getItemInstances(Long itemListId) {
        return itemInstanceDao.listAll(filter("itemListId =", itemListId), orderBy("index"));
    }

    public void computeItemOrder() {
        Iterator<Checkin> checkins = checkinDao.findAll(orderBy("-timestamp")).iterator();
        if (checkins.hasNext()) {
            Checkin checkin = checkins.next();
            long timestamp = checkin.getTimestamp();
            Iterable<Tick> ticks = tickDao.findAll(filter("timestamp >=", timestamp));
            Iterable<ItemOrder> itemOrders = itemOrderDao.findAll(filter("locationId =", checkin.getLocationId()));
            Map<Long, ItemOrder> itemOrderMap = new HashMap<Long, ItemOrder>();
            for (ItemOrder itemOrder : itemOrders) {
                itemOrderMap.put(itemOrder.getItemId(), itemOrder);
            }
            itemOrders = new ItemOrderEngine().createOrder(ticks, itemOrderMap);
            for (ItemOrder itemOrder : itemOrders) {
                itemOrderDao.save(itemOrder);
                Iterable<ItemInstance> itemInstances = itemInstanceDao.findAll(filter("itemId =", itemOrder.getItemId()));
                for (ItemInstance itemInstance : itemInstances) {
                    itemInstance.setIndex(itemOrder.getIndex());
                    itemInstanceDao.save(itemInstance);
                }
            }
        }
    }
}
