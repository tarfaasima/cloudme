package org.cloudme.webgallery.stripes.action.organize;

import java.util.Collection;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.cloudme.webgallery.Gallery;
import org.cloudme.webgallery.service.GenericService;
import org.cloudme.webgallery.stripes.util.AbstractActionBean;

@UrlBinding("/p/organize")
public class OrganizeActionBean extends AbstractActionBean {
    @SpringBean
    private GenericService<Gallery> service;
    
    public Collection<Gallery> getGalleryList() {
        return service.findAll();
    }
    
    @DefaultHandler
    public Resolution show() {
        return new ForwardResolution(getJspPath("/organize/index"));
    }
}
