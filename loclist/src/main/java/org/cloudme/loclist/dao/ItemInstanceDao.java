package org.cloudme.loclist.dao;

import java.util.List;

import org.cloudme.loclist.model.ItemInstance;

public class ItemInstanceDao extends AbstractDao<ItemInstance> {
    public ItemInstanceDao() {
        super(ItemInstance.class);
    }

    public List<ItemInstance> listByItemList(Long itemListId) {
        return listAll(filter("itemListId", itemListId));
    }
}
