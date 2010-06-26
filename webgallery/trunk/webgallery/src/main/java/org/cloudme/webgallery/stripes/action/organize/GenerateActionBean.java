package org.cloudme.webgallery.stripes.action.organize;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.service.PhotoDataService;
import org.cloudme.webgallery.stripes.action.AbstractActionBean;

import com.google.inject.Inject;

@UrlBinding( "/organize/generate/{id}" )
public class GenerateActionBean extends AbstractActionBean {
    @Inject
    private PhotoDataService photoDataService;
    private long id;

    @DefaultHandler
    public Resolution generate() {
        photoDataService.generate(id, ContentType.JPEG);
        return new Resolution() {
            public void execute(HttpServletRequest request,
                    HttpServletResponse response) throws Exception {
            }
        };
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
