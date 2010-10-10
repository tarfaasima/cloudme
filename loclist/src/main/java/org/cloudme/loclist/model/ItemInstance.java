package org.cloudme.loclist.model;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Unindexed;

@Cached
public class ItemInstance {
    @Id
    private Long id;
    private Long itemListId;
    private Long itemId;
    @Unindexed
    private String attribute;
    private int index;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
