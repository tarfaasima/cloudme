package org.cloudme.webgallery.cache;

import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.image.ImageFormat;

public interface CacheService {
	byte[] cachePhoto(Long photoId, ImageFormat format, ContentType type, CacheProducer<byte[]> cacheProducer);

    void invalidate(Long photoId);
}
