package org.cloudme.loclist.model;

import javax.persistence.Transient;

import org.cloudme.gaestripes.DomainObject;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Unindexed;

@Cached
public class ItemInstance extends DomainObject implements Comparable<ItemInstance> {
    private Long itemId;
    private Long itemListId;
    @Unindexed
    private String attribute;
    @Unindexed
    private boolean ticked;
    private String text;
    @Transient
    private boolean inList = false;

    public Long getItemListId() {
        return itemListId;
    }

    public void setItemListId(Long itemListId) {
        this.itemListId = itemListId;
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

    public void setInList(boolean inList) {
        this.inList = inList;
    }

    public boolean isInList() {
        return inList;
    }

    @Override
    public int compareTo(ItemInstance o) {
        return text.compareTo(o.text);
    }
}
