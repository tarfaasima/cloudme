package org.cloudme.webgallery.service;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.cloudme.webgallery.cache.CacheService;
import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.image.DefaultImageFormat;
import org.cloudme.webgallery.model.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheUpdateService {
    @Autowired
    private PhotoDataService photoDataService;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private CacheService cacheService;
    private static final Logger LOG = Logger.getLogger(CacheUpdateService.class);
    
    public void invalidate() {
        cacheService.invalidate();
    }

    public void update() {
        Collection<Photo> photos = photoService.findAll();
        int successful = 0;
        try {
            for (Photo photo : photos) {
                updateCache(photo.getId());
                successful++;
            }
        }
        catch (Exception e) {
            LOG.error("Cache update failed.", e);
        }
        LOG.info(successful + " of " + photos.size() + " photos updated.");
    }

    private void updateCache(Long photoId) {
        for (DefaultImageFormat format : DefaultImageFormat.values()) {
            photoDataService.getPhotoData(photoId, format, ContentType.JPEG);
        }
    }
}
