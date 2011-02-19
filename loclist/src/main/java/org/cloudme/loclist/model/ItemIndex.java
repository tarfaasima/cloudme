package org.cloudme.loclist.model;

import org.cloudme.gaestripes.DomainObject;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Unindexed;

/**
 * Defines the order of {@link Item}s on each {@link Location}. Independent of
 * lists.
 * 
 * @author Moritz Petersen
 */
@Cached
public class ItemIndex extends DomainObject implements Comparable<ItemIndex> {
    @Unindexed
    private int index = -1;
    private Long itemId;
    private Long locationId;
    private Long lastUpdate;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemId() {
        return itemId;
    }

    @Override
    public int compareTo(ItemIndex o) {
        if (index == -1) {
            if (o.index == -1) {
                return 0;
            }
            return 1;
        }
        if (o.index == -1) {
            return -1;
        }
        return index - o.index;
    }

    @Override
    public String toString() {
        return "[itemId = " + itemId + ", index = " + index + "]";
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLastUpdate(Long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }
}
