package org.cloudme.loclist.note;

import com.google.inject.AbstractModule;

public class NoteModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(NoteDao.class);
        bind(NoteItemDao.class);
        bind(NoteService.class);
    }
}
