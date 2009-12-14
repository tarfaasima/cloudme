package org.cloudme.webgallery.stripes.action.organize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.apache.commons.io.IOUtils;
import org.cloudme.webgallery.Gallery;
import org.cloudme.webgallery.Photo;
import org.cloudme.webgallery.service.GenericService;
import org.cloudme.webgallery.stripes.util.AbstractActionBean;

import com.google.appengine.api.datastore.Key;

@UrlBinding("/organize/photo/{galleryId}/${event}/{id}")
public class PhotoActionBean extends AbstractActionBean {
    private Gallery gallery;
    @SpringBean
    private GenericService<Long, Gallery> galleryService;
    @SpringBean
    private GenericService<Key, Photo> photoService;

    public void setPhotoFile(FileBean photoFile) throws IOException {
        System.out.println(photoFile.getFileName());
        System.out.println(IOUtils.toByteArray(photoFile.getInputStream()).length);
        System.out.println(photoFile.getContentType());
        System.out.println(photoFile.getSize());
        Photo photo = new Photo();
        photo.setImageDataAsArray(IOUtils.toByteArray(photoFile.getInputStream()));
//        photo.setGallery(gallery);
        gallery.addPhoto(photo);
        photoService.save(photo);
        galleryService.save(gallery);
    }

    public Resolution upload() {
//        galleryService.save(gallery);
        return new RedirectResolution("/organize/photo/" + gallery.getId() + "/edit");
    }
    
    public List<Photo> getAllPhotos() {
        return new ArrayList<Photo>(photoService.findAll());
    }
    
    @DefaultHandler
    public Resolution edit() {
        return new ForwardResolution(getJspPath("/organize/photo"));
    }

    public void setGalleryId(Long galleryId) {
        System.out.println("galleryId = " + galleryId);
        setGallery(galleryService.find(galleryId));
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    public Gallery getGallery() {
        return gallery;
    }
}
