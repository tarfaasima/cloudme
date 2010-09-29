package org.cloudme.loclist.guice;

import org.cloudme.loclist.dao.LocationDao;
import org.cloudme.loclist.location.LocationService;

import com.google.inject.AbstractModule;

public class LoclistModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(LocationDao.class);
        bind(LocationService.class);
    }
}
