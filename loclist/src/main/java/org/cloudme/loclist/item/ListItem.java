package org.cloudme.loclist.item;

import org.cloudme.loclist.model.Item;

public class ListItem extends Item {
    private boolean isInList;
    private String attribute;

    public boolean isInList() {
        return isInList;
    }

    public void setInList(boolean isInList) {
        this.isInList = isInList;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
