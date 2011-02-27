package org.cloudme.loclist.model;

import org.cloudme.gaestripes.DomainObject;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Unindexed;

/**
 * Time and exact geocoordinates of the user checkin. Also contains the
 * reference to the consolidated {@link Location}.
 * 
 * @author Moritz Petersen
 * @deprecated Only use {@link Location}
 */
@Deprecated
@Cached
public class Checkin extends DomainObject {
    @Unindexed
    private Long locationId;
    private long timestamp;
    @Unindexed
    private float latitude;
    @Unindexed
    private float longitude;

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
