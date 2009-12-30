package org.cloudme.webgallery.stripes.action;

import java.util.Collection;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.cloudme.webgallery.Photo;
import org.cloudme.webgallery.service.PhotoService;

@UrlBinding("/gallery/home")
public class HomeActionBean extends AbstractActionBean {
    @SpringBean
    private PhotoService service;
    
    @DefaultHandler
    public Resolution show() {
        return new ForwardResolution(getJspPath("/gallery/home"));
    }
    
    public Collection<Photo> getPhotos() {
        return service.findAll();
    }
}
