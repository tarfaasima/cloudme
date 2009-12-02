package org.cloudme.webgallery.stripes.action.admin;

import java.util.Collection;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.cloudme.webgallery.Gallery;
import org.cloudme.webgallery.GalleryService;
import org.cloudme.webgallery.stripes.util.AbstractActionBean;

@UrlBinding("/gallery/admin")
public class AdminIndexActionBean extends AbstractActionBean {
    @SpringBean
    private GalleryService galleryService;
    
    public Collection<Gallery> getGalleryList() {
        return galleryService.listAll();
    }
    
    @Override
    @DefaultHandler
    public Resolution show() {
        return new ForwardResolution(getJspPath("/gallery/admin/index"));
    }
}
