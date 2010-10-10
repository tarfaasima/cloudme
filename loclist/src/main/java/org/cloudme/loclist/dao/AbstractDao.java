package org.cloudme.loclist.dao;

import org.cloudme.gaestripes.BaseDao;
import org.cloudme.loclist.model.Checkin;
import org.cloudme.loclist.model.Item;
import org.cloudme.loclist.model.ItemInstance;
import org.cloudme.loclist.model.ItemList;
import org.cloudme.loclist.model.ItemOrder;
import org.cloudme.loclist.model.Location;
import org.cloudme.loclist.model.Tick;

public abstract class AbstractDao<T> extends BaseDao<T> {
    {
        register(Checkin.class);
        register(Item.class);
        register(ItemInstance.class);
        register(ItemList.class);
        register(ItemOrder.class);
        register(Location.class);
        register(Tick.class);
    }

    public AbstractDao(Class<T> baseClass) {
        super(baseClass);
    }
}
