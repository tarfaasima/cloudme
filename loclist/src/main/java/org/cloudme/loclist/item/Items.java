package org.cloudme.loclist.item;

import java.util.ArrayList;
import java.util.List;

import org.cloudme.loclist.model.Item;

public class Items {
    private final List<Item> itemsInList = new ArrayList<Item>();
    private final List<Item> itemsNotInList = new ArrayList<Item>();

    public Items() {
    }

    public void addItemInList(Item item) {
        itemsInList.add(item);
    }

    public void addItemNotInList(Item item) {
        itemsNotInList.add(item);
    }

    public List<Item> getItemsInList() {
        return itemsInList;
    }

    public List<Item> getItemsNotInList() {
        return itemsNotInList;
    }
}
