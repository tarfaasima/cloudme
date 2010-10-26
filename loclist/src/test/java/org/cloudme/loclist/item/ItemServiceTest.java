package org.cloudme.loclist.item;

import static org.cloudme.gaestripes.BaseDao.orderBy;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.cloudme.loclist.dao.TickDao;
import org.cloudme.loclist.location.LocationService;
import org.cloudme.loclist.model.Checkin;
import org.cloudme.loclist.model.Item;
import org.cloudme.loclist.model.ItemInstance;
import org.cloudme.loclist.model.ItemList;
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
    private TickDao tickDao;

    @Before
    public void generateTestData() {
        createItems("Milk", "Cheese", "Tea", "Bread", "Sugar", "Update status report");
        createItemList("Shopping List", "Milk", "Cheese", "Tea", "Bread", "Sugar");
        createItemList("My Todo List", "Update status report");
        
        assertEquals(2, itemListDao.listAll().size());
    }

    @Test
    public void testTick() {
        Checkin checkin = locationService.checkin(53.480712f, -2.234376f);
        itemService.tick(checkin.getId(), itemInstance("Milk").getId());
        itemService.tick(checkin.getId(), itemInstance("Cheese").getId());
        assertEquals(2, tickDao.listAll(orderBy("timestamp")).size());
    }

    @Test
    public void testGetItemList() {
        Checkin checkin = locationService.checkin(53.480712f, -2.234376f);

        List<ItemList> itemLists = itemService.getItemLists();
        assertEquals(2, itemLists.size());
        assertEquals("My Todo List", itemLists.get(0).getName());
        assertEquals("Shopping List", itemLists.get(1).getName());

        ItemList shoppingList = itemLists.get(1);
        assertItemInstanceOrder(checkin, shoppingList, "Milk", "Cheese", "Tea", "Bread", "Sugar");

        simulateTicks(checkin, "Cheese", "Bread");

        itemService.updateItemOrder();

        assertItemInstanceOrder(checkin, shoppingList, "Cheese", "Bread", "Milk", "Tea", "Sugar");
    }

    private void simulateTicks(Checkin checkin, String... texts) {
        for (String text : texts) {
            itemService.tick(checkin.getId(), itemInstance(text).getId());
        }
    }

    private void assertItemInstanceOrder(Checkin checkin, ItemList itemList, String... texts) {
        List<ItemInstance> itemInstances = itemService.getItemInstances(checkin.getId(), itemList.getId());
        assertEquals(texts.length, itemInstances.size());
        for (int i = 0; i < texts.length; i++) {
            Long itemId = itemInstances.get(i).getItemId();
            Item item = itemDao.find(itemId);
            String text = texts[i];
            assertEquals(text, item.getText());
        }
    }
}
