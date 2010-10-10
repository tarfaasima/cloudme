package org.cloudme.loclist.item;

import static org.cloudme.gaestripes.BaseDao.orderBy;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final Map<String, ItemInstance> itemInstanceCache = new HashMap<String, ItemInstance>();

    @Before
    public void createItems() {
        ItemList shoppingList = createItemList("Shopping List");
        milkInstance = createItem(shoppingList, "Milk", "2");
        cheeseInstance = createItem(shoppingList, "Cheese", null);
        createItem(shoppingList, "Tea", null);
        createItem(shoppingList, "Bread", null);
        createItem(shoppingList, "Sugar", null);

        ItemList todoList = createItemList("My Todo List");
        createItem(todoList, "Update status report", null);
        
        assertEquals(2, itemListDao.listAll().size());
    }

    private ItemInstance createItem(ItemList itemList, String text, String attribute) {
        Item item = new Item();
        item.setText(text);
        itemService.put(item);

        ItemInstance itemInstance = new ItemInstance();
        itemInstance.setItemId(item.getId());
        itemInstance.setItemListId(itemList.getId());
        itemInstance.setAttribute(attribute);
        itemService.put(itemInstance);

        itemInstanceCache.put(text, itemInstance);

        return itemInstance;
    }

    private ItemList createItemList(String name) {
        ItemList shoppingList = new ItemList();
        shoppingList.setName(name);
        itemService.put(shoppingList);
        return shoppingList;
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
        assertItemInstanceOrder(shoppingList, "Milk", "Cheese", "Tea", "Bread", "Sugar");

        simulateTicks(manchester, "Cheese", "Bread");

        itemService.computeItemOrder();

        assertItemInstanceOrder(shoppingList, "Cheese", "Bread", "Milk", "Tea", "Sugar");
    }

    private void simulateTicks(Location location, String... texts) {
        for (String text : texts) {
            itemService.tick(location.getId(), itemInstanceCache.get(text).getId());
        }
    }

    private void assertItemInstanceOrder(ItemList itemList, String... texts) {
        List<ItemInstance> itemInstances = itemService.getItemInstances(itemList.getId());
        assertEquals(texts.length, itemInstances.size());
        for (int i = 0; i < texts.length; i++) {
            Long itemId = itemInstances.get(i).getItemId();
            Item item = itemDao.find(itemId);
            String text = texts[i];
            assertEquals(text, item.getText());
        }
    }
}
