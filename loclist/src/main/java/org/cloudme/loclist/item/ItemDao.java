package org.cloudme.loclist.item;

import org.cloudme.gaestripes.AbstractDao;

class ItemDao extends AbstractDao<Item> {
    public ItemDao() {
        super(Item.class);
    }

    @Override
    public Iterable<Item> findAll() {
        return findBy(orderBy("text"));
    }

    public Item findSingleByText(String text) {
        return findSingle("text", text);
    }
}
