package org.cloudme.webgallery.service;

import org.cloudme.webgallery.Photo;
import org.cloudme.webgallery.persistence.Repository;
import org.cloudme.webgallery.persistence.jdo.JdoPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;

@Service
public class PhotoService extends GenericService<String, Photo> {
    @Autowired
    private JdoPhotoRepository repository;
    
    public byte[] getPhotoData(String photoId, String format, String type) {
        Photo photo = find(photoId);
        System.out.println(photo.getData());
        Image image = ImagesServiceFactory.makeImage(photo.getDataAsArray());
        Transform transform = ImagesServiceFactory.makeResize(64, 64);
        return ImagesServiceFactory.getImagesService().applyTransform(transform, image).getImageData();
    }

    @Override
    protected Repository<String, Photo> getRepository() {
        return repository;
    }
}
