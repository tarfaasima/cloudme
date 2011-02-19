package org.cloudme.loclist.dao;

import java.util.List;

import org.cloudme.loclist.model.NoteItem;

import com.googlecode.objectify.Query;

public class NoteItemDao extends BaseDao<NoteItem> {
    public NoteItemDao() {
        super(NoteItem.class);
    }

    public List<NoteItem> listByNote(Long itemListId) {
        return ((Query<NoteItem>) findByItemList(itemListId)).list();
    }

    public Iterable<NoteItem> findByItemList(Long itemListId) {
        return findAll(filter("noteId", itemListId), orderBy("text"));
    }
}
