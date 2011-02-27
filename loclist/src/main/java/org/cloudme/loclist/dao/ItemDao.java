package org.cloudme.loclist.dao;

import org.cloudme.loclist.model.Item;

public class ItemDao extends BaseDao<Item> {
    public ItemDao() {
        super(Item.class);
    }

    @Override
    public Iterable<Item> findAll() {
        return findBy(orderBy("text"));
    }

    public Item findSingleByText(String text) {
        return findSingleBy("text", text);
    }
}
