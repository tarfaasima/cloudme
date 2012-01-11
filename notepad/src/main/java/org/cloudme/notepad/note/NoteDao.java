package org.cloudme.notepad.note;

import org.cloudme.sugar.AbstractDao;

class NoteDao extends AbstractDao<Note> {

    public NoteDao() {
        super(Note.class);
    }

}
