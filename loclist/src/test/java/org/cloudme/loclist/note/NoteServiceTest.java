package org.cloudme.loclist.note;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.cloudme.loclist.test.BaseTestCase;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Inject;

public class NoteServiceTest extends BaseTestCase {
    @Inject
    private NoteService noteService;
    @Inject
    private NoteItemDao noteItemDao;

    @Before
    public void generateTestData() {
        createItems("Milk", "Cheese", "Tea", "Bread", "Sugar", "Update status report");
        createNote("Shopping List", "Milk", "Cheese", "Tea", "Bread", "Sugar");
        createNote("My Todo List", "Update status report");

        assertEquals(2, noteService.listAll().size());
    }

    @Test
    public void testDelete() {
        Note note = note("Shopping List");
        Long id = note.getId();
        assertEquals(5, noteService.getNoteItems(note).size());
        noteService.delete(id);
        assertNull(noteService.find(id));
        assertEquals(0, noteService.getNoteItems(note).size());
    }

    @Test
    public void testAddOrRemove() {
        Note note = note("Shopping List");
        assertInList("Shopping List", "Bread", "Cheese", "Milk", "Sugar", "Tea");

        noteService.addOrRemove(note, item("Milk"), null);
        assertInList("Shopping List", "Bread", "Cheese", "Sugar", "Tea");

        noteService.addOrRemove(note, item("Tea"), null);
        assertInList("Shopping List", "Bread", "Cheese", "Sugar");

        noteService.addOrRemove(note, item("Milk"), "2l");
        assertInList("Shopping List", "Bread", "Cheese", "Milk", "Sugar");
    }

    private void assertInList(String name, String... texts) {
        List<String> textList = new ArrayList<String>(Arrays.asList(texts));
        Collection<NoteItem> noteItems = noteService.getNoteItems(note(name));
        assertEquals("Expected " + textList + " but was: " + texts(noteItems),
                texts.length,
                noteItems.size());
        int i = 0;
        for (NoteItem noteItem : noteItems) {
            if (texts[i++].equals(noteItem.getText())) {
                textList.remove(noteItem.getText());
            }
        }
        assertTrue("Items not in note: " + textList, textList.isEmpty());
    }

    private String texts(Collection<NoteItem> noteItems) {
        StringBuilder sb = new StringBuilder();
        for (NoteItem noteItem : noteItems) {
            if (sb.length() == 0) {
                sb.append("[");
            }
            else {
                sb.append(", ");
            }
            sb.append(noteItem.getText());
        }
        sb.append("]");
        return sb.toString();
    }

    @Test
    public void testGetAllNoteItems() {
        Collection<NoteItem> allNoteItems = noteService.getAllNoteItems(note("Shopping List"));
        boolean[] expectedInNoteStatus = new boolean[] { true, true, true, true, true, false };
        assertEquals(expectedInNoteStatus.length, allNoteItems.size());
        int i = 0;
        for (NoteItem noteItem : allNoteItems) {
            assertEquals(expectedInNoteStatus[i++], noteItem.isInNote());
        }
    }

    @Test
    public void testGetNoteItem() {
        NoteItem noteItem = noteService.getNoteItem(noteItem("Milk").getId());
        assertEquals("Milk", noteItem.getText());
    }

    @Test
    public void testGetNoteItems() {
        List<NoteItem> noteItems = noteService.getNoteItems(note("Shopping List"));
        assertEquals(5, noteItems.size());
    }

    @Test
    public void testPutNoteItem() {
        Note note = note("Shopping List");
        assertEquals(5, noteService.getNoteItems(note).size());
        NoteItem noteItem = new NoteItem();
        noteItem.setAttribute("1");
        noteItem.setItemId(2000L);
        noteItem.setNoteId(note.getId());
        noteService.put(noteItem);
        assertEquals(6, noteService.getNoteItems(note).size());
    }

    @Test
    public void testDeleteNoteItemByItemId() {
        assertNotNull(noteItemDao.find(noteItem("Milk").getId()));
        noteService.deleteNoteItemByItemId(item("Milk").getId());
        assertNull(noteItemDao.find(noteItem("Milk").getId()));
    }

}
