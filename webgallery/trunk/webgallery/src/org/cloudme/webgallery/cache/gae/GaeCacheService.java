package org.cloudme.webgallery.cache.gae;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;

import org.cloudme.webgallery.cache.CacheKey;
import org.cloudme.webgallery.cache.CacheProducer;
import org.cloudme.webgallery.cache.CacheService;
import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.image.ImageFormat;
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
    public byte[] cachePhoto(Long photoId, ImageFormat format, ContentType type, CacheProducer<byte[]> cacheProducer) {
        CacheKey key = new CacheKey(format, type);
        Map<CacheKey, byte[]> dataMap;
        if (cache.containsKey(photoId)) {
            dataMap = (Map<CacheKey, byte[]>) cache.get(photoId);
        }
        else {
            dataMap = new HashMap<CacheKey, byte[]>();
            cache.put(photoId, dataMap);    
        }
        if (dataMap.containsKey(key)) {
            return dataMap.get(key);
        }
        else {
            byte[] output = cacheProducer.produce();
            dataMap.put(key, output);
            return output;
        }
    }

    public void invalidate(Long photoId) {
        cache.remove(photoId);
    }
}
