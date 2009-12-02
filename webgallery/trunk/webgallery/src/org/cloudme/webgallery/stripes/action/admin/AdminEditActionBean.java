package org.cloudme.webgallery.stripes.action.admin;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import org.cloudme.webgallery.Gallery;
import org.cloudme.webgallery.GalleryService;
import org.cloudme.webgallery.stripes.util.AbstractActionBean;
import org.springframework.stereotype.Component;

@Component
@UrlBinding("/gallery/admin/edit/{galleryId}")
public class AdminEditActionBean extends AbstractActionBean {
    @ValidateNestedProperties({
        @Validate(field="name", required=true)
    })
    private Gallery gallery;
    @SpringBean
    private GalleryService galleryService;
    
    @Override
    @DefaultHandler
    @DontValidate
    public Resolution show() {
        return new ForwardResolution(getJspPath("/gallery/admin/edit"));
    }
    
    public Resolution save() {
        galleryService.create(gallery);
        return new RedirectResolution(AdminIndexActionBean.class);
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    public Gallery getGallery() {
        return gallery;
    }
}
