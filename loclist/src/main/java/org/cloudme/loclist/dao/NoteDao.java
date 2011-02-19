package org.cloudme.loclist.dao;

import org.cloudme.loclist.model.Note;

public class NoteDao extends BaseDao<Note> {
    public NoteDao() {
        super(Note.class);
    }
}
