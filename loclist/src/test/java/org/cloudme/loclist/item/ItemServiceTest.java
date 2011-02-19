package org.cloudme.loclist.item;

import static org.cloudme.gaestripes.AbstractDao.filter;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.cloudme.loclist.dao.ItemIndexDao;
import org.cloudme.loclist.location.LocationService;
import org.cloudme.loclist.model.Checkin;
import org.cloudme.loclist.model.Item;
import org.cloudme.loclist.model.Note;
import org.cloudme.loclist.model.NoteItem;
import org.cloudme.loclist.test.AbstractServiceTestCase;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Inject;

public class ItemServiceTest extends AbstractServiceTestCase {
    @Inject
    private ItemIndexDao itemIndexDao;
    @Inject
    private ItemService itemService;
    @Inject
    private LocationService locationService;

    // @Inject
    // private TickDao tickDao;

    @Before
    public void generateTestData() {
        createItems("Milk", "Cheese", "Tea", "Bread", "Sugar", "Update status report");
        createNote("Shopping List", "Milk", "Cheese", "Tea", "Bread", "Sugar");
        createNote("My Todo List", "Update status report");

        assertEquals(2, noteDao.listAll().size());
    }

    @Test
    public void testCreateItem() {
        Item milk = item("Milk");
        assertNotNull(milk);
        assertEquals(6, itemDao.listAll().size());

        Item item = new Item();
        item.setText("Milk");
        itemService.createItem(note("Shopping List").getId(), item, "");

        assertEquals(milk.getId(), item.getId());
        assertEquals(6, itemDao.listAll().size());
    }

    @Test
    public void testRemoveNoteItem() {
        assertEquals(6, noteItemDao.listAll().size());
        Long noteId = note("Shopping List").getId();
        Long itemId = item("Milk").getId();

        itemService.addOrRemove(noteId, itemId, "");

        assertEquals(5, noteItemDao.listAll().size());
    }

    @Test( expected = IllegalStateException.class )
    public void testCreateNoteItemIllegalState() {
        assertEquals(6, noteItemDao.listAll().size());
        Long noteId = note("Shopping List").getId();
        Long itemId = item("Milk").getId();

        NoteItem noteItem = new NoteItem();
        noteItem.setItemId(itemId);
        noteItem.setNoteId(noteId);
        noteItemDao.save(noteItem);

        try {
            itemService.addOrRemove(noteId, itemId, "");
        }
        catch (IllegalStateException e) {
            System.out.println("If you can see this, the test is successful: " + e.getMessage());
            throw e;
        }

        fail("Exception should have been thrown.");
    }

    //
    // @Test
    // public void testTick() {
    // Checkin checkin = locationService.checkin(53.480712f, -2.234376f);
    // itemService.tick(checkin.getId(), itemInstance("Milk").getId());
    // itemService.tick(checkin.getId(), itemInstance("Cheese").getId());
    // assertEquals(2, tickDao.listAll(orderBy("timestamp")).size());
    // }

    @Test
    public void testGetNote() {
        Checkin checkin = locationService.checkin(53.480712f, -2.234376f);

        List<Note> notes = itemService.getNotes();
        assertEquals(2, notes.size());
        assertEquals("My Todo List", notes.get(0).getName());
        assertEquals("Shopping List", notes.get(1).getName());

        Note shoppingList = notes.get(1);
        assertNoteItemIndex(checkin, shoppingList, "Bread", "Cheese", "Milk", "Sugar", "Tea");

        simulateTicks(checkin, "Cheese", "Bread");

        // itemService.updateItemIndex();

        assertNoteItemIndex(checkin, shoppingList, "Cheese", "Bread", "Milk", "Sugar", "Tea");
    }

    @Test
    public void testAddOrRemove() {
        Long noteId = note("Shopping List").getId();
        assertInList("Shopping List", "Milk", "Cheese", "Tea", "Bread", "Sugar");

        itemService.addOrRemove(noteId, item("Milk").getId(), null);
        assertInList("Shopping List", "Cheese", "Tea", "Bread", "Sugar");

        itemService.addOrRemove(noteId, item("Tea").getId(), null);
        assertInList("Shopping List", "Cheese", "Bread", "Sugar");

        itemService.addOrRemove(noteId, item("Milk").getId(), "2l");
        assertInList("Shopping List", "Milk", "Cheese", "Bread", "Sugar");
    }

    private void assertInList(String name, String... texts) {
        List<String> textList = new ArrayList<String>(Arrays.asList(texts));
        Collection<NoteItem> noteItems = itemService.getAllNoteItems(note(name).getId());
        for (NoteItem noteItem : noteItems) {
            if (noteItem.isInList()) {
                textList.remove(noteItem.getText());
            }
        }
        assertTrue("Items not in note: " + textList, textList.isEmpty());
    }

    @Test
    public void testDeleteNote() {
        Long noteId = note("Shopping List").getId();
        assertEquals(5, noteItemDao.listAll(filter("noteId", noteId)).size());

        itemService.deleteNote(noteId);

        refresh();
        assertEquals(0, noteItemDao.listAll(filter("noteId", noteId)).size());
        assertNull(noteDao.find(noteId));
    }

    @Test
    public void testDeleteItem() {
        Long itemId = item("Tea").getId();
        Long checkinId = locationService.checkin(1.0F, 1.0F).getId();
        itemService.tick(checkinId, noteItem("Tea").getId());
        itemService.tick(checkinId, noteItem("Milk").getId());
        // assertEquals(2, tickDao.listAll().size());
        // itemService.updateItemIndex();
        assertEquals(2, itemIndexDao.listAll().size());

        itemService.deleteItem(itemId);

        refresh();
        assertNull(itemDao.find(itemId));
        assertEquals(0, noteItemDao.listAll(filter("itemId", itemId)).size());
        assertEquals(1, itemIndexDao.listAll().size());
        // assertEquals(1, tickDao.listAll().size());
    }

    private void simulateTicks(Checkin checkin, String... texts) {
        for (String text : texts) {
            itemService.tick(checkin.getId(), noteItem(text).getId());
        }
    }

    private void assertNoteItemIndex(Checkin checkin, Note note, String... texts) {
        List<NoteItem> noteItems = itemService.getNoteItems(note.getId());
        itemService.orderByCheckin(checkin.getId(), noteItems);
        assertEquals(texts.length, noteItems.size());
        for (int i = 0; i < texts.length; i++) {
            Long itemId = noteItems.get(i).getItemId();
            Item item = itemDao.find(itemId);
            String text = texts[i];
            assertEquals(text, item.getText());
        }
    }
}
