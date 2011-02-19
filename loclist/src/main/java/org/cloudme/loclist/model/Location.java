package org.cloudme.loclist.model;

import org.cloudme.gaestripes.DomainObject;

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
public class Location extends DomainObject {
    @Unindexed
    private GeoPt geoPt;

    public Location() {
        // Required for persistence
    }

    public Location(float latitude, float longitude) {
        this.setGeoPt(new GeoPt(latitude, longitude));
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
