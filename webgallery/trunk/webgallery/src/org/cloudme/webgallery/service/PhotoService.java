package org.cloudme.webgallery.service;

import org.cloudme.webgallery.Photo;
import org.cloudme.webgallery.image.ImageParameter;
import org.cloudme.webgallery.image.ImageParameterFactory;
import org.cloudme.webgallery.image.ImageService;
import org.cloudme.webgallery.persistence.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoService extends AbstractService<String, Photo> {
    @Autowired
    private ImageService imageService;
    
    @Autowired
    protected PhotoService(PhotoRepository repository) {
        super(repository);
    }

    public byte[] getPhotoData(String photoId, String size, String format) {
        Photo photo = find(photoId);
        ImageParameter parameter = ImageParameterFactory.getImageParameter(size);
        return imageService.process(photo.getDataAsArray(), parameter, format);
    }
}
