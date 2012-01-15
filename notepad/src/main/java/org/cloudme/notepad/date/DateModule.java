package org.cloudme.notepad.date;

import com.google.inject.AbstractModule;

public class DateModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DateService.class);
    }

}
