package org.cloudme.loclist.note;

import org.cloudme.loclist.item.Item;
import org.cloudme.sugar.Entity;

import com.googlecode.objectify.annotation.Cached;

/**
 * A list of {@link Item}s.
 * 
 * @author Moritz Petersen
 */
@Cached
public class Note extends Entity {
    private String name;

    public Note() {
        // empty constructor
    }

    public Note(Long id) {
        super(id);
    }

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
