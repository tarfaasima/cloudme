package org.cloudme.loclist.item;


import org.cloudme.sugar.AbstractService;

import com.google.inject.Inject;

public class ItemService extends AbstractService<Item> {
    private final ItemDao itemDao;

    @Inject
    public ItemService(ItemDao itemDao) {
        super(itemDao);
        this.itemDao = itemDao;
    }

    @Override
    public Item put(Item item) {
        Item existingItem = itemDao.findSingleByText(item.getText());
        if (existingItem == null) {
            return super.put(item);
        }
        return existingItem;
    }
}
