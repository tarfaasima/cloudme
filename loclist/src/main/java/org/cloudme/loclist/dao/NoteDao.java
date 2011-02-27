package org.cloudme.loclist.dao;

import java.util.List;

import org.cloudme.loclist.model.Note;

public class NoteDao extends BaseDao<Note> {
    public NoteDao() {
        super(Note.class);
    }

    @Override
    public List<Note> listAll() {
        return super.listBy(orderBy("name"));
    }

    public Note findSingleByName(String name) {
        return findSingleBy("name", name);
    }
}
