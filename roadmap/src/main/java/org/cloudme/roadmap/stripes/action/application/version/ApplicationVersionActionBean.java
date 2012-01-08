package org.cloudme.roadmap.stripes.action.application.version;

import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.roadmap.application.version.ApplicationVersion;
import org.cloudme.roadmap.application.version.ApplicationVersionService;
import org.cloudme.sugar.AbstractActionBean;
import org.cloudme.sugar.Id;

import com.google.inject.Inject;

@UrlBinding( "/_/applicationVersion/{applicationVersion.applicationId}/{$event}/{applicationVersion.id}" )
@Getter
@Setter
public class ApplicationVersionActionBean extends AbstractActionBean {
    @Inject
    private ApplicationVersionService applicationVersionService;
    private ApplicationVersion applicationVersion;

    @DefaultHandler
    public Resolution edit() {
        if (applicationVersion != null && applicationVersion.getId() != null) {
            applicationVersion = applicationVersionService.find(Id.of(applicationVersion));
        }
        return resolve("edit.jsp");
    }

    public Resolution save() {
        applicationVersionService.put(applicationVersion);
        return redirect("/_/applicationVersion/"
                + applicationVersion.getApplicationId()
                + "/edit/"
                + applicationVersion.getId());
    }

    public Resolution saveAndExit() {
        applicationVersionService.put(applicationVersion);
        return redirect("/_/application/" + applicationVersion.getApplicationId());
    }
}
