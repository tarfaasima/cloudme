package org.cloudme.webgallery.stripes.action.organize;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.service.PhotoDataService;
import org.cloudme.webgallery.stripes.action.AbstractActionBean;

@UrlBinding( "/organize/generate/{id}" )
public class GenerateActionBean extends AbstractActionBean {
    @SpringBean
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
