package org.cloudme.loclist.location;

import java.util.Iterator;
import java.util.List;

import org.cloudme.loclist.item.Item;
import org.cloudme.sugar.AbstractDao;

class ItemIndexDao extends AbstractDao<ItemIndex> {
    public ItemIndexDao() {
        super(ItemIndex.class);
    }

    /**
     * Finds all {@link ItemIndex}s by {@link Location}.
     * 
     * @param location
     *            The {@link Location}
     * @return The {@link ItemIndex}s of the {@link Location}
     */
    public Iterable<ItemIndex> findBy(Location location) {
        return findBy(filter("locationId", location.getId()));
    }

    /**
     * Finds the {@link ItemIndex} by {@link Location} and {@link Item}.
     * 
     * @param location
     *            The {@link Location}
     * @param item
     *            The {@link Item}
     * @return The {@link ItemIndex} or <tt>null</tt> if the {@link ItemIndex}
     *         does not exist
     */
    public ItemIndex findBy(Location location, Item item) {
        return findSingle(filter("locationId", location.getId()), filter("itemId", item.getId()));
    }

    /**
     * Finds the last updated {@link ItemIndex} for the given {@link Location}.
     * 
     * @param location
     *            The {@link Location}
     * @return The last updated {@link ItemIndex} or <tt>null</tt> if no
     *         {@link ItemIndex} exists for the given {@link Location}
     */
    public ItemIndex findLastBy(Location location) {
        Iterable<ItemIndex> itemIndexs = findBy(filter("locationId", location.getId()), orderBy("-lastUpdate"));
        if (itemIndexs == null) {
            return null;
        }
        Iterator<ItemIndex> it = itemIndexs.iterator();
        if (!it.hasNext()) {
            return null;
        }
        return it.next();
    }

    public void deleteByItemId(Long itemId) {
        deleteAll(filter("itemId", itemId));
    }

    public List<ItemIndex> listBy(Location location) {
        return super.listBy(filter("locationId", location.getId()));
    }
}
