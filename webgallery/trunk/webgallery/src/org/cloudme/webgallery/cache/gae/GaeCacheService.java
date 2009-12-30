package org.cloudme.webgallery.cache.gae;

import java.util.Collections;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;

import org.cloudme.webgallery.cache.CacheKey;
import org.cloudme.webgallery.cache.CacheProducer;
import org.cloudme.webgallery.cache.CacheService;
import org.cloudme.webgallery.image.ImageParameter;
import org.springframework.stereotype.Component;

@Component
public class GaeCacheService implements CacheService {
    private final Cache cache;

    public GaeCacheService() throws CacheException {
        CacheManager cacheManager = CacheManager.getInstance();
        CacheFactory cacheFactory = cacheManager.getCacheFactory();
        cache = cacheFactory.createCache(Collections.EMPTY_MAP);
    }

    @SuppressWarnings("unchecked")
    public byte[] cachePhoto(String photoId, ImageParameter parameter, String format, CacheProducer<byte[]> cacheProducer) {
        CacheKey key = new CacheKey(photoId, parameter, format);
        if (cache.containsKey(key)) {
            return (byte[]) cache.get(key);
        }
        else {
            byte[] output = cacheProducer.produce();
            cache.put(key, output);
            return output;
        }
    }
}
