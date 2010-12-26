package org.cloudme.loclist.item;

import static org.cloudme.gaestripes.BaseDao.filter;
import static org.cloudme.gaestripes.BaseDao.orderBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.cloudme.loclist.dao.ItemOrderDao;
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
    private ItemOrderDao itemOrderDao;
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
    public void testCreateItem() {
        Item milk = item("Milk");
        assertNotNull(milk);
        assertEquals(6, itemDao.listAll().size());

        Item item = new Item();
        item.setText("Milk");
        itemService.createItem(itemList("Shopping List").getId(), item, "");

        assertEquals(milk.getId(), item.getId());
        assertEquals(6, itemDao.listAll().size());
    }

    @Test
    public void testRemoveItemInstance() {
        assertEquals(6, itemInstanceDao.listAll().size());
        Long itemListId = itemList("Shopping List").getId();
        Long itemId = item("Milk").getId();

        itemService.addOrRemove(itemListId, itemId, "");

        assertEquals(5, itemInstanceDao.listAll().size());
    }

    @Test( expected = IllegalStateException.class )
    public void testCreateItemInstanceIllegalState() {
        assertEquals(6, itemInstanceDao.listAll().size());
        Long itemListId = itemList("Shopping List").getId();
        Long itemId = item("Milk").getId();

        ItemInstance itemInstance = new ItemInstance();
        itemInstance.setItemId(itemId);
        itemInstance.setItemListId(itemListId);
        itemInstanceDao.save(itemInstance);

        try {
            itemService.addOrRemove(itemListId, itemId, "");
        }
        catch (IllegalStateException e) {
            System.out.println("If you can see this, the test is successful: " + e.getMessage());
            throw e;
        }

        fail("Exception should have been thrown.");
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
        assertItemInstanceOrder(checkin, shoppingList, "Bread", "Cheese", "Milk", "Sugar", "Tea");

        simulateTicks(checkin, "Cheese", "Bread");

        itemService.updateItemOrder();

        assertItemInstanceOrder(checkin, shoppingList, "Cheese", "Bread", "Milk", "Sugar", "Tea");
    }

    @Test
    public void testAddOrRemove() {
        Long itemListId = itemList("Shopping List").getId();
        assertInList("Shopping List", "Milk", "Cheese", "Tea", "Bread", "Sugar");

        itemService.addOrRemove(itemListId, item("Milk").getId(), null);
        assertInList("Shopping List", "Cheese", "Tea", "Bread", "Sugar");

        itemService.addOrRemove(itemListId, item("Tea").getId(), null);
        assertInList("Shopping List", "Cheese", "Bread", "Sugar");

        itemService.addOrRemove(itemListId, item("Milk").getId(), "2l");
        assertInList("Shopping List", "Milk", "Cheese", "Bread", "Sugar");
    }

    private void assertInList(String name, String... texts) {
        List<String> textList = new ArrayList<String>(Arrays.asList(texts));
        Collection<ItemInstance> itemInstances = itemService.getAllItemInstances(itemList(name).getId());
        for (ItemInstance itemInstance : itemInstances) {
            if (itemInstance.isInList()) {
                textList.remove(itemInstance.getText());
            }
        }
        assertTrue("Items not in list: " + textList, textList.isEmpty());
    }

    @Test
    public void testDeleteItemList() {
        Long itemListId = itemList("Shopping List").getId();
        assertEquals(5, itemInstanceDao.listAll(filter("itemListId", itemListId)).size());

        itemService.deleteItemList(itemListId);

        refresh();
        assertEquals(0, itemInstanceDao.listAll(filter("itemListId", itemListId)).size());
        assertNull(itemListDao.find(itemListId));
    }

    @Test
    public void testDeleteItem() {
        Long itemId = item("Tea").getId();
        Long checkinId = locationService.checkin(1.0F, 1.0F).getId();
        itemService.tick(checkinId, itemInstance("Tea").getId());
        itemService.tick(checkinId, itemInstance("Milk").getId());
        assertEquals(2, tickDao.listAll().size());
        itemService.updateItemOrder();
        assertEquals(2, itemOrderDao.listAll().size());

        itemService.deleteItem(itemId);

        refresh();
        assertNull(itemDao.find(itemId));
        assertEquals(0, itemInstanceDao.listAll(filter("itemId", itemId)).size());
        assertEquals(1, itemOrderDao.listAll().size());
        assertEquals(1, tickDao.listAll().size());
    }

    private void simulateTicks(Checkin checkin, String... texts) {
        for (String text : texts) {
            itemService.tick(checkin.getId(), itemInstance(text).getId());
        }
    }

    private void assertItemInstanceOrder(Checkin checkin, ItemList itemList, String... texts) {
        List<ItemInstance> itemInstances = itemService.getItemInstances(itemList.getId());
        itemService.orderByCheckin(checkin.getId(), itemInstances);
        assertEquals(texts.length, itemInstances.size());
        for (int i = 0; i < texts.length; i++) {
            Long itemId = itemInstances.get(i).getItemId();
            Item item = itemDao.find(itemId);
            String text = texts[i];
            assertEquals(text, item.getText());
        }
    }
}
