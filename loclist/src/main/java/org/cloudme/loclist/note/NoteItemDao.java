package org.cloudme.loclist.note;

import java.util.List;

import org.cloudme.loclist.item.Item;
import org.cloudme.sugar.AbstractDao;

import com.googlecode.objectify.Query;

class NoteItemDao extends AbstractDao<NoteItem> {
    public NoteItemDao() {
        super(NoteItem.class);
    }

    public List<NoteItem> listBy(Note note) {
        return ((Query<NoteItem>) findBy(note)).list();
    }

    public List<NoteItem> listBy(Item item) {
        return listBy(filter("itemId", item.getId()));
    }

    public Iterable<NoteItem> findBy(Note note) {
        return findBy(filter("noteId", note.getId()), orderBy("text"));
    }

    public Iterable<NoteItem> findBy(Note note, Item item) {
        return findBy(filter("noteId", note.getId()), filter("itemId", item.getId()));
    }

    public void deleteByItemId(Long itemId) {
        deleteAll(filter("itemId", itemId));
    }

    public void deleteByNoteId(Long noteId) {
        deleteAll(filter("noteId", noteId));
    }

    public NoteItem findSingleBy(Note note, Item item) {
        return findSingle(filter("noteId", note.getId()), filter("itemId", item.getId()));
    }
}
