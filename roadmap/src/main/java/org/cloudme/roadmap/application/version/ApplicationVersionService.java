package org.cloudme.roadmap.application.version;

import java.util.List;

import org.cloudme.roadmap.application.Application;
import org.cloudme.sugar.AbstractService;
import org.cloudme.sugar.Id;

import com.google.inject.Inject;

public class ApplicationVersionService extends AbstractService<ApplicationVersion> {

    private final ApplicationVersionDao dao;

    @Inject
    public ApplicationVersionService(ApplicationVersionDao dao) {
        super(dao);
        this.dao = dao;
    }

    public Iterable<ApplicationVersion> findVersions(Id<Application, Long> id) {
        return dao.listByApplicationId(id);
    }

    public List<ApplicationVersion> listByApplicationId(Id<Application, Long> id) {
        return dao.listByApplicationId(id);
    }

}
