package org.cloudme.webgallery.cache;

import org.cloudme.webgallery.image.ImageParameter;

public interface CacheService {
    byte[] cachePhoto(String photoId, ImageParameter parameter, String format, CacheProducer<byte[]> cacheProducer);
}
