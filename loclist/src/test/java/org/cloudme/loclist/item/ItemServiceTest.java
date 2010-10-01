package org.cloudme.loclist.item;

import org.cloudme.loclist.location.LocationService;
import org.cloudme.loclist.model.Item;
import org.cloudme.loclist.model.ItemInstance;
import org.cloudme.loclist.model.ItemList;
import org.cloudme.loclist.model.Location;
import org.cloudme.loclist.test.AbstractServiceTestCase;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Inject;

public class ItemServiceTest extends AbstractServiceTestCase {
    @Inject
    private ItemService itemService;
    @Inject
    private LocationService locationService;
    private ItemInstance milkInstance;
    private ItemInstance cheeseInstance;

    @Before
    public void createItems() {
        Item milk = new Item();
        milk.setText("Milk");
        Item cheese = new Item();
        cheese.setText("Cheese");
        itemService.put(milk);
        itemService.put(cheese);

        ItemList shoppingList = new ItemList();
        shoppingList.setName("Shopping List");
        itemService.put(shoppingList);

        milkInstance = new ItemInstance();
        milkInstance.setItemId(milk.getId());
        milkInstance.setItemListId(shoppingList.getId());
        milkInstance.setQuantity(2);
        itemService.put(milkInstance);
        
        cheeseInstance = new ItemInstance();
        cheeseInstance.setItemId(cheese.getId());
        cheeseInstance.setItemListId(shoppingList.getId());
        itemService.put(cheeseInstance);
    }

    @Test
    public void testTick() {
        Location manchester = locationService.checkin(53.480712f, -2.234376f);
        itemService.tick(manchester.getId(), milkInstance.getId());
        itemService.tick(manchester.getId(), cheeseInstance.getId());
    }
}
