package org.cloudme.webgallery.cache;

import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.image.ImageFormat;

public interface CacheService {
    byte[] cachePhoto(String photoId, ImageFormat format, ContentType type, CacheProducer<byte[]> cacheProducer);
}
