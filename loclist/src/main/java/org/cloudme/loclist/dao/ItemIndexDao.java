package org.cloudme.loclist.dao;

import java.util.Iterator;

import org.cloudme.loclist.model.ItemIndex;

public class ItemIndexDao extends BaseDao<ItemIndex> {
    public ItemIndexDao() {
        super(ItemIndex.class);
    }

    public Iterable<ItemIndex> findByLocation(Long locationId) {
        return findAll(filter("locationId", locationId));
    }

    public ItemIndex findByLocationAndItem(long locationId, Long itemId) {
        return findSingle(filter("locationId", locationId), filter("itemId", itemId));
    }

    public ItemIndex findLastByLocation(long locationId) {
        Iterable<ItemIndex> itemIndexs = findAll(filter("locationId", locationId), orderBy("-lastUpdate"));
        if (itemIndexs == null) {
            return null;
        }
        Iterator<ItemIndex> it = itemIndexs.iterator();
        if (!it.hasNext()) {
            return null;
        }
        return it.next();
    }
}
