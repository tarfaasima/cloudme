package org.cloudme.notepad.note;

import org.cloudme.sugar.AbstractService;

import com.google.inject.Inject;

public class NoteService extends AbstractService<Note> {

    @Inject
    public NoteService(NoteDao dao) {
        super(dao);
    }

}
