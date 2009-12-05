package org.cloudme.webgallery.stripes.action.organize;

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
import org.cloudme.webgallery.service.GenericService;
import org.cloudme.webgallery.stripes.util.AbstractActionBean;

@UrlBinding("/p/organize/gallery/{gallery.id}/{$event}")
public class GalleryActionBean extends AbstractActionBean {
    @ValidateNestedProperties({
        @Validate(field="name", required=true)
    })
    private Gallery gallery;
    @SpringBean
    private GenericService<Gallery> service;
    
    @DefaultHandler
    @DontValidate
    public Resolution load() {
        if (hasId()) {
            gallery = service.find(gallery.getId());
        }
        return new ForwardResolution(getJspPath("/organize/gallery"));
    }
    
    private boolean hasId() {
        return gallery != null && gallery.getId() > 0;
    }

    public Resolution save() {
        service.save(gallery);
        return new RedirectResolution(OrganizeActionBean.class);
    }
    
    @DontValidate
    public Resolution delete() {
        if (hasId()) {
            service.delete(gallery.getId());
        }
        return new RedirectResolution(OrganizeActionBean.class);
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    public Gallery getGallery() {
        return gallery;
    }
}
