package org.cloudme.loclist.dao;

import org.cloudme.gaestripes.AbstractDao;
import org.cloudme.loclist.model.ItemList;

public class ItemListDao extends AbstractDao<ItemList> {
    public ItemListDao() {
        super(ItemList.class);
    }
}
