package org.cloudme.notepad.note;

import com.google.inject.AbstractModule;

public class NoteModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(NoteDao.class);
        bind(NoteService.class);
    }

}
