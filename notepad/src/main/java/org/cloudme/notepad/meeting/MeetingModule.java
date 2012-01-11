package org.cloudme.notepad.meeting;

import com.google.inject.AbstractModule;

public class MeetingModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(MeetingDao.class);
        bind(MeetingService.class);
    }

}
