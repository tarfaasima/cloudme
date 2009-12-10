package org.cloudme.webgallery.stripes.action.organize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.cloudme.webgallery.Gallery;
import org.cloudme.webgallery.service.GenericService;
import org.cloudme.webgallery.stripes.util.AbstractActionBean;

@UrlBinding("/organize/gallery/{$event}/{deleteGalleryId}")
public class IndexActionBean extends AbstractActionBean {
    @SpringBean
    private GenericService<Gallery> service;
    private List<Gallery> galleries;
    private String deleteGalleryId;

    public String getDeleteGalleryId() {
        return deleteGalleryId;
    }

    @DefaultHandler
    @DontValidate
    public Resolution list() {
        setGalleries(toList(service.findAll()));
        return new ForwardResolution(getJspPath("/organize/index"));
    }

    public Resolution save() {
        for (Gallery gallery : galleries) {
            service.save(gallery);
        }
        return new RedirectResolution(IndexActionBean.class);
    }

    @DontValidate
    public Resolution delete() {
        service.delete(deleteGalleryId);
        return new RedirectResolution(IndexActionBean.class);
    }
    
    public void setDeleteGalleryId(String deleteGalleryId) {
        this.deleteGalleryId = deleteGalleryId;
    }
    
    public Collection<Gallery> getGalleryList() {
        return service.findAll();
    }

    public void setGalleries(List<Gallery> galleries) {
        this.galleries = galleries;
    }

    public List<Gallery> getGalleries() {
        return galleries;
    }
    
    private List<Gallery> toList(Collection<Gallery> collection) {
        if (collection instanceof List<?>) {
            return (List<Gallery>) collection;
        }
        return new ArrayList<Gallery>(collection);
    }
}
