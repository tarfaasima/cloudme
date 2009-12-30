package org.cloudme.webgallery.service;

import java.util.Collections;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;

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
    private final Cache cache;

    @Autowired
    protected PhotoService(PhotoRepository repository) throws CacheException {
        super(repository);
        CacheManager cacheManager = CacheManager.getInstance();
        CacheFactory cacheFactory = cacheManager.getCacheFactory();
        cache = cacheFactory.createCache(Collections.EMPTY_MAP);
    }

    @SuppressWarnings("unchecked")
    public byte[] getPhotoData(String photoId, String size, String format) {
        Photo photo = find(photoId);
        ImageParameter parameter = ImageParameterFactory.getImageParameter(size);
        CacheKey key = new CacheKey(photoId, parameter, format);
        byte[] output;
        if (cache.containsKey(key)) {
            output = (byte[]) cache.get(key);
        }
        else {
            byte[] input = photo.getDataAsArray();
            output = imageService.process(input, parameter, format);
            cache.put(key, output);
        }
        return output;
    }
}
