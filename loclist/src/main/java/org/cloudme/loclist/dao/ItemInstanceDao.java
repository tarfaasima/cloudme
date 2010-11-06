package org.cloudme.loclist.dao;

import java.util.List;

import org.cloudme.loclist.model.ItemInstance;

import com.googlecode.objectify.Query;

public class ItemInstanceDao extends AbstractDao<ItemInstance> {
    public ItemInstanceDao() {
        super(ItemInstance.class);
    }

    public List<ItemInstance> listByItemList(Long itemListId) {
        return ((Query<ItemInstance>) findByItemList(itemListId)).list();
    }

    public Iterable<ItemInstance> findByItemList(Long itemListId) {
        return findAll(filter("itemListId", itemListId), orderBy("text"));
    }
}
