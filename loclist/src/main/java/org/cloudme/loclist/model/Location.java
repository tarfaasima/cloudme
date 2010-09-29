package org.cloudme.loclist.model;

import javax.persistence.Id;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Unindexed;

@Cached
public class Location {
    @Id
    private Long id;
    @Unindexed
    private GeoPt geoPt;

    public Location() {
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
