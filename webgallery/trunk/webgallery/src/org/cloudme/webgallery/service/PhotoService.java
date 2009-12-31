package org.cloudme.webgallery.service;

import javax.cache.CacheException;

import org.cloudme.webgallery.Photo;
import org.cloudme.webgallery.cache.CacheProducer;
import org.cloudme.webgallery.cache.CacheService;
import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.image.ImageFormat;
import org.cloudme.webgallery.image.ImageService;
import org.cloudme.webgallery.persistence.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoService extends AbstractService<String, Photo> {
    @Autowired
    private ImageService imageService;
    @Autowired
    private CacheService cacheService;

    @Autowired
    protected PhotoService(PhotoRepository repository) throws CacheException {
        super(repository);
    }

    public byte[] getPhotoData(final String photoId, final ImageFormat format, final ContentType type) {
        return cacheService.cachePhoto(photoId, format, type, new CacheProducer<byte[]>() {
            public byte[] produce() {
                Photo photo = find(photoId);
                byte[] input = photo.getDataAsArray();
                return imageService.process(input, format, type);
            }
        });
    }
}
