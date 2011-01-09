package org.cloudme.loclist.dao;

import org.cloudme.gaestripes.AbstractDao;
import org.cloudme.loclist.model.Checkin;
import org.cloudme.loclist.model.Item;
import org.cloudme.loclist.model.ItemInstance;
import org.cloudme.loclist.model.ItemList;
import org.cloudme.loclist.model.ItemOrder;
import org.cloudme.loclist.model.Location;
import org.cloudme.loclist.model.Tick;
import org.cloudme.loclist.model.Update;
import org.cloudme.loclist.model.UserLog;

public abstract class BaseDao<T> extends AbstractDao<T> {
    {
        register(Checkin.class);
        register(Item.class);
        register(ItemInstance.class);
        register(ItemList.class);
        register(ItemOrder.class);
        register(Location.class);
        register(Tick.class);
        register(Update.class);
        register(UserLog.class);
    }

    public BaseDao(Class<T> baseClass) {
        super(baseClass);
    }
}
