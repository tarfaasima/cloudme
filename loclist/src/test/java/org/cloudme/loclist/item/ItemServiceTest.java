package org.cloudme.loclist.item;

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
import org.cloudme.loclist.model.Item;
import org.cloudme.loclist.model.Location;
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
        itemService.createItem(note("Shopping List"), item, "");

        assertEquals(milk.getId(), item.getId());
        assertEquals(6, itemDao.listAll().size());
    }

    @Test
    public void testRemoveNoteItem() {
        assertEquals(6, noteItemDao.listAll().size());

        itemService.addOrRemove(note("Shopping List"), item("Milk"), "");

        assertEquals(5, noteItemDao.listAll().size());
    }

    @Test( expected = IllegalStateException.class )
    public void testCreateNoteItemIllegalState() {
        assertEquals(6, noteItemDao.listAll().size());
        Note note = note("Shopping List");
        Long noteId = note.getId();
        Item item = item("Milk");
        Long itemId = item.getId();

        NoteItem noteItem = new NoteItem();
        noteItem.setItemId(itemId);
        noteItem.setNoteId(noteId);
        noteItemDao.save(noteItem);

        try {
            itemService.addOrRemove(note, item, "");
        }
        catch (IllegalStateException e) {
            System.out.println("If you can see this, the test is successful: " + e.getMessage());
            throw e;
        }

        fail("Exception should have been thrown.");
    }

    @Test
    public void testGetNote() {
        Location location = locationService.checkin(53.480712f, -2.234376f);

        List<Note> notes = itemService.getNotes();
        assertEquals(2, notes.size());
        assertEquals("My Todo List", notes.get(0).getName());
        assertEquals("Shopping List", notes.get(1).getName());

        Note shoppingList = notes.get(1);
        assertNoteItemIndex(location, shoppingList, "Bread", "Cheese", "Milk", "Sugar", "Tea");

        simulateTicks(location, "Cheese", "Bread");

        assertNoteItemIndex(location, shoppingList, "Cheese", "Bread", "Milk", "Sugar", "Tea");
    }

    @Test
    public void testAddOrRemove() {
        Note note = note("Shopping List");
        assertInList("Shopping List", "Milk", "Cheese", "Tea", "Bread", "Sugar");

        itemService.addOrRemove(note, item("Milk"), null);
        assertInList("Shopping List", "Cheese", "Tea", "Bread", "Sugar");

        itemService.addOrRemove(note, item("Tea"), null);
        assertInList("Shopping List", "Cheese", "Bread", "Sugar");

        itemService.addOrRemove(note, item("Milk"), "2l");
        assertInList("Shopping List", "Milk", "Cheese", "Bread", "Sugar");
    }

    private void assertInList(String name, String... texts) {
        List<String> textList = new ArrayList<String>(Arrays.asList(texts));
        Collection<NoteItem> noteItems = itemService.getAllNoteItems(note(name));
        for (NoteItem noteItem : noteItems) {
            if (noteItem.isInNote()) {
                textList.remove(noteItem.getText());
            }
        }
        assertTrue("Items not in note: " + textList, textList.isEmpty());
    }

    @Test
    public void testDeleteNote() {
        Note note = note("Shopping List");
        assertEquals(5, noteItemDao.listBy(note).size());

        itemService.deleteNote(note.getId());

        refresh();
        assertEquals(0, noteItemDao.listBy(note).size());
        assertNull(noteDao.find(note.getId()));
    }

    @Test
    public void testDeleteItem() {
        Item item = item("Tea");
        Location location = locationService.checkin(1.0F, 1.0F);
        itemService.tick(location, noteItem("Tea"));
        itemService.tick(location, noteItem("Milk"));
        assertEquals(2, itemIndexDao.listAll().size());

        itemService.deleteItem(item.getId());

        refresh();
        assertNull(itemDao.find(item.getId()));
        assertEquals(0, noteItemDao.listBy(item).size());
        assertEquals(1, itemIndexDao.listAll().size());
    }

    private void simulateTicks(Location location, String... texts) {
        for (String text : texts) {
            itemService.tick(location, noteItem(text));
        }
    }

    private void assertNoteItemIndex(Location location, Note note, String... texts) {
        List<NoteItem> noteItems = itemService.getNoteItems(note);
        itemService.orderByLocation(location, noteItems);
        assertEquals(texts.length, noteItems.size());
        for (int i = 0; i < texts.length; i++) {
            Long itemId = noteItems.get(i).getItemId();
            Item item = itemDao.find(itemId);
            String text = texts[i];
            assertEquals(text, item.getText());
        }
    }
}
