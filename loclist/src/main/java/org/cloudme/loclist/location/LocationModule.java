package org.cloudme.loclist.location;

import com.google.inject.AbstractModule;

public class LocationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ItemIndexDao.class);
        bind(LocationDao.class);
        bind(LocationService.class);
    }
}
