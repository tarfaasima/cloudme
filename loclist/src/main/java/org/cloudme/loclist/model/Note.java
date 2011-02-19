package org.cloudme.loclist.model;

import org.cloudme.gaestripes.DomainObject;

import com.googlecode.objectify.annotation.Cached;

/**
 * A list of {@link Item}s.
 * 
 * @author Moritz Petersen
 */
@Cached
public class Note extends DomainObject {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getId() + ":" + name;
    }
}
