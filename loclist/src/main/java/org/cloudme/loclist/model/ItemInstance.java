package org.cloudme.loclist.model;

import org.cloudme.gaestripes.DomainObject;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Unindexed;

@Cached
public class ItemInstance extends DomainObject {
    private Long itemId;
    private Long itemListId;
    @Unindexed
    private String attribute;
    @Unindexed
    private boolean ticked;
    @Unindexed
    private String text;

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
}
