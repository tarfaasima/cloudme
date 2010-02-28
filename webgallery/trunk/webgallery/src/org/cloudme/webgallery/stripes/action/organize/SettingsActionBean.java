package org.cloudme.webgallery.stripes.action.organize;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import org.cloudme.webgallery.model.FlickrMetaData;
import org.cloudme.webgallery.service.FlickrService;
import org.cloudme.webgallery.service.FlickrService.Perms;
import org.cloudme.webgallery.stripes.action.AbstractActionBean;

@UrlBinding("/organize/settings/{$event}")
public class SettingsActionBean extends AbstractActionBean {
    @SpringBean
    private FlickrService flickrService;
    @ValidateNestedProperties( { 
        @Validate(field = "key", required = true),
        @Validate(field = "secret", required = true) 
    })
    private FlickrMetaData metaData;

    @DefaultHandler
    @DontValidate
    public Resolution show() {
        metaData = flickrService.get();
        return new ForwardResolution(getJspPath("/organize/settings"));
    }

    public Resolution save() {
        flickrService.put(metaData);
        return new ForwardResolution(getJspPath("/organize/settings"));
    }
    
    public String getFlickrLoginLink() {
        return flickrService.createLoginLink(Perms.WRITE);
    }

    public void setMetaData(FlickrMetaData metaData) {
        this.metaData = metaData;
    }

    public FlickrMetaData getMetaData() {
        return metaData;
    }
}
