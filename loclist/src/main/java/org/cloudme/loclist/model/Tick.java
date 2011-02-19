package org.cloudme.loclist.model;

import org.cloudme.gaestripes.DomainObject;

import com.googlecode.objectify.annotation.Cached;

/**
 * Records a tick of a user, referencing the {@link Checkin} and the
 * {@link Item}.
 * 
 * @author Moritz Petersen
 */
@Cached
@Deprecated
public class Tick extends DomainObject {
    private Long checkinId;
    private Long itemId;
    private long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public Long getCheckinId() {
        return checkinId;
    }

    public void setCheckinId(Long checkinId) {
        this.checkinId = checkinId;
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
}
