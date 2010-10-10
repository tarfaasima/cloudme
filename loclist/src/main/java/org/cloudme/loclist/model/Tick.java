package org.cloudme.loclist.model;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;

@Cached
public class Tick {
    @Id
    private Long id;
    private Long locationId;
    private Long itemInstanceId;
    private long timestamp;
    private Long itemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getItemInstanceId() {
        return itemInstanceId;
    }

    public void setItemInstanceId(Long itemInstanceId) {
        this.itemInstanceId = itemInstanceId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "itemId = " + itemId;
    }
}
