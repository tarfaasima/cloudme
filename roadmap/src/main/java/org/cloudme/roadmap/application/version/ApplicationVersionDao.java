package org.cloudme.roadmap.application.version;

import java.util.List;

import org.cloudme.roadmap.application.Application;
import org.cloudme.sugar.AbstractDao;
import org.cloudme.sugar.Id;

class ApplicationVersionDao extends AbstractDao<ApplicationVersion> {

    public ApplicationVersionDao() {
        super(ApplicationVersion.class);
    }

    public List<ApplicationVersion> listByApplicationId(Id<Application, Long> id) {
        return listBy(filter("applicationId", id.value()));
    }

}
