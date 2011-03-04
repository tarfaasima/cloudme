package org.cloudme.loclist.note;

import java.util.List;

import org.cloudme.gaestripes.AbstractDao;

class NoteDao extends AbstractDao<Note> {
    public NoteDao() {
        super(Note.class);
    }

    @Override
    public List<Note> listAll() {
        return super.listBy(orderBy("name"));
    }

    public Note findSingleByName(String name) {
        return findSingle("name", name);
    }
}
