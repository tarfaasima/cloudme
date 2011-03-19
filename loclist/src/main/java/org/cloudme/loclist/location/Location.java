package org.cloudme.loclist.location;

import org.cloudme.sugar.Entity;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Unindexed;

/**
 * A consolidated location; referred to by {@link Checkin}s and
 * {@link ItemIndex}s.
 * 
 * @author Moritz Petersen
 * 
 */
@Cached
public class Location extends Entity {
    @Unindexed
    private GeoPt geoPt;

    public Location() {
        // Required for persistence
    }

    public Location(float latitude, float longitude) {
        this.setGeoPt(new GeoPt(latitude, longitude));
    }

    public Location(Long id) {
        super(id);
    }

    public void setGeoPt(GeoPt geoPt) {
        this.geoPt = geoPt;
    }

    public GeoPt getGeoPt() {
        return geoPt;
    }

    public float getLatitude() {
        return geoPt.getLatitude();
    }

    public float getLongitude() {
        return geoPt.getLongitude();
    }
}
