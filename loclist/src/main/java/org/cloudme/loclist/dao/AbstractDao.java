package org.cloudme.loclist.dao;

import org.cloudme.gaestripes.BaseDao;
import org.cloudme.loclist.model.Location;

public abstract class AbstractDao<T> extends BaseDao<T> {
    {
        register(Location.class);
    }

    public AbstractDao(Class<T> baseClass) {
        super(baseClass);
    }
}
