package org.cloudme.roadmap.application.version;

import com.google.inject.AbstractModule;

public class ApplicationVersionModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ApplicationVersionDao.class);
        bind(ApplicationVersionService.class);
    }

}
