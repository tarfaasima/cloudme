package org.cloudme.loclist.model;

import java.util.Date;

import org.cloudme.gaestripes.DomainObject;

import com.googlecode.objectify.annotation.Cached;

@Cached
public class ItemList extends DomainObject {
    private String name;
    private Date lastUpdate;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }
}
