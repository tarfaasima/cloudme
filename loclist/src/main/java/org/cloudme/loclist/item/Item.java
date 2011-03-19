package org.cloudme.loclist.item;

import org.cloudme.loclist.note.Note;
import org.cloudme.sugar.Entity;

import com.googlecode.objectify.annotation.Cached;

/**
 * An item of the list - essentially just the text of the item. It can be used
 * in any {@link Note}.
 * 
 * @author Moritz Petersen
 */
@Cached
public class Item extends Entity {
    private String text;

    public Item(Long id) {
        super(id);
    }

    public Item() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return getId() + ":" + text;
    }
}
