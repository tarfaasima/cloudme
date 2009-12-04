package org.cloudme.webgallery.stripes.action.admin;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.cloudme.webgallery.Gallery;
import org.cloudme.webgallery.service.GenericService;
import org.cloudme.webgallery.stripes.util.AbstractActionBean;

@UrlBinding("/gallery/admin/delete/{galleryId}")
public class AdminDeleteActionBean extends AbstractActionBean {
    @SpringBean
    private GenericService<Gallery> galleryService;
    private long galleryId;
    
    @Override
    @DefaultHandler
    public Resolution show() {
        galleryService.delete(galleryId);
        return new RedirectResolution(AdminIndexActionBean.class);
    }
    
    public void setGalleryId(long galleryId) {
        this.galleryId = galleryId;
    }

    public long getGalleryId() {
        return galleryId;
    }
}
