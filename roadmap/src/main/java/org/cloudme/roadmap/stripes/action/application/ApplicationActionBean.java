package org.cloudme.roadmap.stripes.action.application;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import org.cloudme.roadmap.application.Application;
import org.cloudme.roadmap.application.ApplicationService;
import org.cloudme.roadmap.application.version.ApplicationVersion;
import org.cloudme.roadmap.application.version.ApplicationVersionService;
import org.cloudme.sugar.AbstractActionBean;
import org.cloudme.sugar.Id;

import com.google.inject.Inject;

@UrlBinding( "/_/application/{$event}/{application.id}" )
@Getter
@Setter
public class ApplicationActionBean extends AbstractActionBean {
    @Inject
    private ApplicationService applicationService;
    @Inject
    private ApplicationVersionService applicationVersionService;
    private List<Application> applications;
    private List<ApplicationVersion> applicationVersions;
    @ValidateNestedProperties( { @Validate( field = "name", required = true, minlength = 3, maxlength = 50 ) } )
    private Application application;

    @DontValidate
    @DefaultHandler
    public Resolution index() {
        applications = applicationService.listAll();
        return resolve("index.jsp");
    }

    @DontValidate
    public Resolution edit() {
        if (application != null) {
			val id = Id.of(application);
            application = applicationService.find(id);
            applicationVersions = applicationVersionService.listByApplicationId(id);
        }
        return resolve("edit.jsp");
    }

    public Resolution save() {
        applicationService.put(application);
        return redirect("/_/application/edit/" + application.getId());
    }

    public Resolution saveAndExit() {
        applicationService.put(application);
        return redirect("/_/application");
    }

    @DontValidate
    public Resolution delete() {
        if (application != null) {
            applicationService.delete(Id.of(application));
        }
        return redirect("/_/application");
    }
}
