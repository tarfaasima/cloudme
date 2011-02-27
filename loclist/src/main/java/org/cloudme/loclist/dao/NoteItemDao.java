package org.cloudme.loclist.dao;

import java.util.List;

import org.cloudme.loclist.model.Item;
import org.cloudme.loclist.model.Note;
import org.cloudme.loclist.model.NoteItem;

import com.googlecode.objectify.Query;

public class NoteItemDao extends BaseDao<NoteItem> {
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
