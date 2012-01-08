package org.cloudme.roadmap.application;

import com.google.inject.AbstractModule;

public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ApplicationDao.class);
        bind(ApplicationService.class);
    }
}
