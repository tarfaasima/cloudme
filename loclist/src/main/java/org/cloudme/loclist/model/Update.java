package org.cloudme.loclist.model;


import org.cloudme.gaestripes.DomainObject;

import com.googlecode.objectify.annotation.Unindexed;

public class Update extends DomainObject {
    @Unindexed
    private long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
