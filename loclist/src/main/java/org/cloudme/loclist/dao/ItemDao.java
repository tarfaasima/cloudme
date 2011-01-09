package org.cloudme.loclist.dao;

import org.cloudme.loclist.model.Item;

public class ItemDao extends BaseDao<Item> {
    public ItemDao() {
        super(Item.class);
    }
}
