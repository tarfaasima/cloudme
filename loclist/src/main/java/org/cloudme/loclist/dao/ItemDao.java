package org.cloudme.loclist.dao;

import org.cloudme.gaestripes.AbstractDao;
import org.cloudme.loclist.model.Item;

public class ItemDao extends AbstractDao<Item> {
    public ItemDao() {
        super(Item.class);
    }
}
