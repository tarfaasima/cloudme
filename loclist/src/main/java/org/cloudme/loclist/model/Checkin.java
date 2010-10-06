package org.cloudme.loclist.model;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Unindexed;

public class Checkin {
    @Id
    private Long id;
    @Unindexed
    private Long locationId;
    private long timestamp;
    @Unindexed
    private float latitude;
    @Unindexed
    private float longitude;

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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
