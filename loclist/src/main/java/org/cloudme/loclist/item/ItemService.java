package org.cloudme.loclist.item;

import static org.cloudme.gaestripes.BaseDao.filter;
import static org.cloudme.gaestripes.BaseDao.orderBy;

import java.util.List;

import org.cloudme.loclist.dao.ItemDao;
import org.cloudme.loclist.dao.ItemInstanceDao;
import org.cloudme.loclist.dao.ItemListDao;
import org.cloudme.loclist.dao.TickDao;
import org.cloudme.loclist.model.Item;
import org.cloudme.loclist.model.ItemInstance;
import org.cloudme.loclist.model.ItemList;
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
        tick.setTimestamp(System.currentTimeMillis());
        tickDao.save(tick);
    }

    public List<ItemList> getItemLists() {
        return itemListDao.listAll(orderBy("name"));
    }

    public List<ItemInstance> getItemInstances(Long itemListId) {
        return itemInstanceDao.listAll(filter("itemListId =", itemListId));
    }

    public void computeItemOrder() {
    }
}
