package org.cloudme.loclist.test;

import java.util.HashMap;
import java.util.Map;

import org.cloudme.loclist.item.Item;
import org.cloudme.loclist.item.ItemModule;
import org.cloudme.loclist.location.LocationModule;
import org.cloudme.loclist.note.Note;
import org.cloudme.loclist.note.NoteItem;
import org.cloudme.loclist.note.NoteModule;
import org.cloudme.loclist.user.UserProfileModule;
import org.cloudme.sugar.AbstractDao;
import org.cloudme.sugar.AbstractServiceTestCase;

import com.google.inject.Module;

public abstract class BaseTestCase extends AbstractServiceTestCase {
    private static final Module[] MODULES = { new ItemModule(), new NoteModule(), new LocationModule(),
            new UserProfileModule() };

    private class NoteDao extends AbstractDao<Note> {
        public NoteDao() {
            super(Note.class);
        }
    
        public Note findSingleByName(String name) {
            return findSingle("name", name);
        }
    }

    protected AbstractDao<Item> itemDao = new AbstractDao<Item>(Item.class) {
    };
    private final Map<String, Item> items = new HashMap<String, Item>();
    protected NoteDao noteDao = new NoteDao();
    protected AbstractDao<NoteItem> noteItemDao = new AbstractDao<NoteItem>(NoteItem.class) {
    };
    private final Map<String, NoteItem> noteItems = new HashMap<String, NoteItem>();

    protected void createItems(String... texts) {
        for (String text : texts) {
            Item item = new Item();
            item.setText(text);
            itemDao.put(item);
            items.put(text, item);
        }
    }

    protected void createNote(String name, String... texts) {
        Note note = new Note();
        note.setName(name);
        noteDao.put(note);
    
        for (String text : texts) {
            Item item = items.get(text);
            NoteItem noteItem = new NoteItem();
            noteItem.setItemId(item.getId());
            noteItem.setNoteId(note.getId());
            noteItem.setText(text);
            noteItemDao.put(noteItem);
            noteItems.put(text, noteItem);
        }
    }

    protected Item item(String text) {
        return items.get(text);
    }

    protected Note note(String name) {
        return noteDao.findSingleByName(name);
    }

    protected NoteItem noteItem(String text) {
        return noteItems.get(text);
    }

    protected void refresh() {
        noteItems.clear();
        for (NoteItem noteItem : noteItemDao.findAll()) {
            noteItems.put(noteItem.getText(), noteItem);
        }
        items.clear();
        for (Item item : itemDao.findAll()) {
            items.put(item.getText(), item);
        }
    }

    @Override
    protected Module[] getModules() {
        return MODULES;
    }

}
