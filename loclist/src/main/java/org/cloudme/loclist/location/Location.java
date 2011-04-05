package org.cloudme.loclist.location;

import java.util.Collection;

import javax.persistence.Transient;

import org.cloudme.sugar.Entity;

import com.google.appengine.api.datastore.Blob;
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
    @Unindexed
    private Blob thumbnail;
    @Transient
    private Collection<ItemIndex> itemIndexs;

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

    public void setThumbnail(Blob thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Blob getThumbnail() {
        return thumbnail;
    }

    public void setThumbnailBytes(byte[] bytes) {
        this.thumbnail = new Blob(bytes);
    }

    public byte[] getThumbnailBytes() {
        return thumbnail == null ? null : thumbnail.getBytes();
    }

    public void setItemIndexs(Collection<ItemIndex> itemIndexs) {
        this.itemIndexs = itemIndexs;
    }

    public Collection<ItemIndex> getItemIndexs() {
        return itemIndexs;
    }
}
