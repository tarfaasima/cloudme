package org.cloudme.loclist.model;

import org.cloudme.gaestripes.DomainObject;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Unindexed;

@Cached
public class ItemOrder extends DomainObject implements Comparable<ItemOrder> {
    @Unindexed
    private int index = -1;
    private Long itemId;
    private Long locationId;

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
    public int compareTo(ItemOrder o) {
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
}
