package org.cloudme.loclist.dao;

import org.cloudme.loclist.model.ItemOrder;

public class ItemOrderDao extends AbstractDao<ItemOrder> {
    public ItemOrderDao() {
        super(ItemOrder.class);
    }

    public Iterable<ItemOrder> findByLocation(Long locationId) {
        return findAll(filter("locationId", locationId));
    }
}
