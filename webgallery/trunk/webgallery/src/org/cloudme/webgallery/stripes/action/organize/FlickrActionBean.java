package org.cloudme.webgallery.stripes.action.organize;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;

import org.cloudme.webgallery.service.FlickrService;
import org.cloudme.webgallery.stripes.action.AbstractActionBean;

@UrlBinding("/organize/flickr/{$event}")
public class FlickrActionBean extends AbstractActionBean {
    @Validate(required = true)
    private String frob;
    
    @SpringBean
    private FlickrService flickrService;
    
    @DefaultHandler
    public Resolution login() {
        addMessage(flickrService.flickrAuthGetToken(frob));
        return new RedirectResolution(SettingsActionBean.class);
    }

    public void setFrob(String frob) {
        this.frob = frob;
    }

    public String getFrob() {
        return frob;
    }
}
