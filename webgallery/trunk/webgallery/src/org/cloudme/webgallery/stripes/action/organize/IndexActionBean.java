package org.cloudme.webgallery.stripes.action.organize;

import java.util.Collection;

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

@UrlBinding("/organize")
public class IndexActionBean extends AbstractActionBean {
    @ValidateNestedProperties( { @Validate(field = "name", required = true) })
    private Gallery gallery;
    @SpringBean
    private GenericService<Gallery> service;

    public Collection<Gallery> getGalleryList() {
        return service.findAll();
    }

    @DefaultHandler
    @DontValidate
    public Resolution show() {
        return new ForwardResolution(getJspPath("/organize/index"));
    }

    public Resolution save() {
        service.save(gallery);
        // return new ForwardResolution(getJspPath("/organize/index"));
        return new RedirectResolution(IndexActionBean.class);
    }

    @DontValidate
    public Resolution delete() {
        service.delete(gallery.getId());
        // return new ForwardResolution(getJspPath("/organize/index"));
        return new RedirectResolution(IndexActionBean.class);
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    public Gallery getGallery() {
        return gallery;
    }
}
