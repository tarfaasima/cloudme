package org.cloudme.loclist.item;

import static org.cloudme.gaestripes.BaseDao.filter;
import static org.cloudme.gaestripes.BaseDao.orderBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.Iterator;
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

	@Test
	public void testGetItems() {
		createItemList("Shopping List 2", "Tea", "Bread", "Sugar");

        List<Item> items = itemService.getItems(itemList("Shopping List 2").getId()).getItemsNotInList();

		Iterator<Item> it = items.iterator();
		assertEquals("Cheese", it.next().getText());
		assertEquals("Milk", it.next().getText());
		assertEquals("Update status report", it.next().getText());
		assertFalse(it.hasNext());
	}

	@Test
	public void testAddToItemList() {
		createItemList("Shopping List 2");
		Long itemListId = itemList("Shopping List 2").getId();
		Long checkinId = locationService.checkin(1.0f, 1.0f).getId();

		assertEquals(0, itemService.getItemInstancesInItemList(checkinId, itemListId).size());
        assertEquals(6, itemService.getItems(itemListId).getItemsNotInList().size());

		itemService.addToItemList(itemListId, item("Milk").getId());

        assertEquals(5, itemService.getItems(itemListId).getItemsNotInList().size());

		itemService.addToItemList(itemListId, item("Cheese").getId());

        assertEquals(4, itemService.getItems(itemListId).getItemsNotInList().size());
	}

	@Test
	public void testDeleteItemList() {
		Long itemListId = itemList("Shopping List").getId();
		assertEquals(5, itemInstanceDao.listAll(filter("itemListId", itemListId)).size());

		itemService.deleteItemList(itemListId);

		refreshItemInstances();
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
		
		refreshItemInstances();
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
		List<ItemInstance> itemInstances = itemService.getItemInstancesInItemList(checkin.getId(), itemList.getId());
		assertEquals(texts.length, itemInstances.size());
		for (int i = 0; i < texts.length; i++) {
			Long itemId = itemInstances.get(i).getItemId();
			Item item = itemDao.find(itemId);
			String text = texts[i];
			assertEquals(text, item.getText());
		}
	}
}
