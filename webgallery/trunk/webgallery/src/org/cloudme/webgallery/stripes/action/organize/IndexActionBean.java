package org.cloudme.webgallery.stripes.action.organize;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

@UrlBinding("/organize/{$event}/{deleteGalleryId}")
public class IndexActionBean extends AbstractActionBean {
    @SpringBean
    private GenericService<Gallery> service;
    private Map<String, Gallery> galleryMap;
    private String deleteGalleryId;

    public String getDeleteGalleryId() {
        return deleteGalleryId;
    }

    @DefaultHandler
    @DontValidate
    public Resolution show() {
        setGalleryMap(createMap(service.findAll()));
        return new ForwardResolution(getJspPath("/organize/index"));
    }

    public Resolution save() {
        for (Gallery gallery : galleryMap.values()) {
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

    public void setGalleryMap(Map<String, Gallery> galleryMap) {
        this.galleryMap = galleryMap;
    }

    public Map<String, Gallery> getGalleryMap() {
        return galleryMap;
    }
    
    private Map<String, Gallery> createMap(Collection<Gallery> galleries) {
        HashMap<String, Gallery> galleryMap = new HashMap<String, Gallery>(galleries.size());
        for (Gallery gallery : galleries) {
            galleryMap.put(gallery.getId(), gallery);
        }
        return galleryMap;
    }
}
