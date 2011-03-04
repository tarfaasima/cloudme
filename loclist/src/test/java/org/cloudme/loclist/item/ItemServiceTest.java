package org.cloudme.loclist.item;

import static org.junit.Assert.assertEquals;

import org.cloudme.loclist.note.NoteService;
import org.cloudme.loclist.test.BaseTestCase;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Inject;

public class ItemServiceTest extends BaseTestCase {
    @Inject
    private ItemService itemService;
    @Inject
    private NoteService noteService;

    @Before
    public void generateTestData() {
        createItems("Milk", "Cheese", "Tea", "Bread", "Sugar", "Update status report");
        createNote("Shopping List", "Milk", "Cheese", "Tea", "Bread", "Sugar");
        createNote("My Todo List", "Update status report");

        assertEquals(2, noteService.listAll().size());
    }

    @Test
    public void testPut() {
        assertEquals(6, itemService.listAll().size());

        Item bananas = new Item();
        bananas.setText("Bananas");
        itemService.put(bananas);

        assertEquals(7, itemService.listAll().size());

        Item item = new Item();
        item.setText("Bananas");
        item = itemService.put(item);

        assertEquals(bananas.getId(), item.getId());
        assertEquals(7, itemService.listAll().size());
    }
}
