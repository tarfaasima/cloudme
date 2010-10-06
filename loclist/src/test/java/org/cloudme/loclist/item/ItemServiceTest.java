package org.cloudme.loclist.item;

import static org.cloudme.gaestripes.BaseDao.orderBy;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.cloudme.loclist.dao.ItemDao;
import org.cloudme.loclist.dao.ItemListDao;
import org.cloudme.loclist.dao.TickDao;
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
    @Inject
    private ItemDao itemDao;
    @Inject
    private ItemListDao itemListDao;
    @Inject
    private TickDao tickDao;
    private ItemInstance milkInstance;
    private ItemInstance cheeseInstance;
    private ItemInstance statusUpdateInstance;

    @Before
    public void createItems() {
        ItemList shoppingList = new ItemList();
        shoppingList.setName("Shopping List");
        itemService.put(shoppingList);

        Item milk = new Item();
        milk.setText("Milk");
        itemService.put(milk);

        milkInstance = new ItemInstance();
        milkInstance.setItemId(milk.getId());
        milkInstance.setItemListId(shoppingList.getId());
        milkInstance.setAttribute("2");
        itemService.put(milkInstance);
        
        Item cheese = new Item();
        cheese.setText("Cheese");
        itemService.put(cheese);

        cheeseInstance = new ItemInstance();
        cheeseInstance.setItemId(cheese.getId());
        cheeseInstance.setItemListId(shoppingList.getId());
        itemService.put(cheeseInstance);

        ItemList todoList = new ItemList();
        todoList.setName("My Todo List");
        itemService.put(todoList);

        Item statusUpdate = new Item();
        statusUpdate.setText("Update status report");
        itemService.put(statusUpdate);

        statusUpdateInstance = new ItemInstance();
        statusUpdateInstance.setItemId(statusUpdate.getId());
        statusUpdateInstance.setItemListId(todoList.getId());
        itemService.put(statusUpdateInstance);
        
        assertEquals(2, itemListDao.listAll().size());
    }

    @Test
    public void testTick() {
        Location manchester = locationService.checkin(53.480712f, -2.234376f);
        itemService.tick(manchester.getId(), milkInstance.getId());
        itemService.tick(manchester.getId(), cheeseInstance.getId());
        assertEquals(2, tickDao.listAll(orderBy("timestamp")).size());
    }

    @Test
    public void testGetItemList() {
        Location manchester = locationService.checkin(53.480712f, -2.234376f);

        List<ItemList> itemLists = itemService.getItemLists();
        assertEquals(2, itemLists.size());
        assertEquals("My Todo List", itemLists.get(0).getName());
        assertEquals("Shopping List", itemLists.get(1).getName());

        ItemList shoppingList = itemLists.get(1);
        assertItemInstanceOrder(itemService.getItemInstances(shoppingList.getId()), "Milk", "Cheese");

        itemService.tick(manchester.getId(), itemService.getItemInstances(shoppingList.getId()).get(1).getId());
        itemService.tick(manchester.getId(), itemService.getItemInstances(shoppingList.getId()).get(0).getId());

        itemService.computeItemOrder();

        assertItemInstanceOrder(itemService.getItemInstances(shoppingList.getId()), "Cheese", "Milk");
    }

    private void assertItemInstanceOrder(List<ItemInstance> itemInstances, String... texts) {
        assertEquals(texts.length, itemInstances.size());
        for (int i = 0; i < texts.length; i++) {
            Long itemId = itemInstances.get(i).getItemId();
            Item item = itemDao.find(itemId);
            String text = texts[i];
            assertEquals(text, item.getText());
        }
    }
}
