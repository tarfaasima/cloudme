package org.cloudme.loclist.item;

import static org.cloudme.gaestripes.BaseDao.filter;
import static org.cloudme.gaestripes.BaseDao.orderBy;

import java.util.Iterator;
import java.util.List;

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
        Tick tick = new Tick();
        tick.setCheckinId(checkinId);
        tick.setItemId(itemInstance.getItemId());
        tick.setTimestamp(System.currentTimeMillis());
        tickDao.save(tick);
    }

    public List<ItemList> getItemLists() {
        return itemListDao.listAll(orderBy("name"));
    }

    public List<ItemInstance> getItemInstances(Long checkinId, Long itemListId) {
        return itemInstanceDao.listAll(filter("itemListId =", itemListId), orderBy("index"));
    }

    public void computeItemOrder() {
        // Capture current timestamp
        Update currentUpdate = new Update();
        currentUpdate.setTimestamp(System.currentTimeMillis());
        
        // Load last update timestamp
        Iterator<Update> updates = updateDao.findAll(orderBy("-timestamp")).iterator();
        if (!updates.hasNext()) {
            return;
        }
        Update lastUpdate = updates.next();
        
        // Load checkins between current and last timestamp
        Iterator<Checkin> checkins = checkinDao.findAll(orderBy("-timestamp")).iterator();
        // For each checkin do:
        while (checkins.hasNext()) {
            Checkin checkin = checkins.next();
            if (checkin.getTimestamp() < lastUpdate.getTimestamp()) {
                break;
            }
            // Load ticks
            // Hier geht's weiter.
        }
        // Load item orders and create map
        // Compute new order
        // Save item orders
        // End do.
        // Save current timestamp.
    }
}
