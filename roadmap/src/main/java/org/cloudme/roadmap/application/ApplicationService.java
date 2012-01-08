package org.cloudme.roadmap.application;

import org.cloudme.sugar.AbstractService;

import com.google.inject.Inject;

public class ApplicationService extends AbstractService<Application> {
    @Inject
    public ApplicationService(ApplicationDao dao) {
        super(dao);
    }
}
