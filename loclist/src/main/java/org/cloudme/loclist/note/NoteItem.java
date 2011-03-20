package org.cloudme.loclist.note;

import javax.persistence.Transient;

import org.cloudme.loclist.item.Item;
import org.cloudme.sugar.Entity;

import com.google.inject.internal.ToStringBuilder;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Unindexed;

/**
 * An instance of an {@link Item} attached to an {@link Note}. Contains an
 * attribute (such as quantity), a flag to indicated if it is ticked and the
 * text of the {@link Item}.
 * 
 * @author Moritz Petersen
 */
@Cached
public class NoteItem extends Entity implements Comparable<NoteItem> {
    private Long itemId;
    private Long noteId;
    @Unindexed
    private String attribute;
    @Unindexed
    private boolean ticked;
    private String text;
    @Transient
    private boolean inNote = false;

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setTicked(boolean ticked) {
        this.ticked = ticked;
    }

    public boolean isTicked() {
        return ticked;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setInNote(boolean inNote) {
        this.inNote = inNote;
    }

    public boolean isInNote() {
        return inNote;
    }

    @Override
    public int compareTo(NoteItem o) {
        return text.compareTo(o.text);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(NoteItem.class).add("text", text).add("attribute", attribute).add("ticked", ticked)
                .add("inNote", inNote).toString();
    }
}
