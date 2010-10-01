package org.cloudme.loclist.model;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Unindexed;

public class ItemInstance {
    @Id
    private Long id;
    private Long itemListId;
    private Long itemId;
    @Unindexed
    private int quantity;

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
