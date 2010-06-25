package org.cloudme.webgallery.cache.gae;

import java.io.Serializable;
import java.util.Collections;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;

import org.cloudme.webgallery.cache.CacheProducer;
import org.cloudme.webgallery.cache.CacheService;

public class GaeCacheService implements CacheService {
    private final Cache cache;

    public GaeCacheService() {
        CacheManager cacheManager = CacheManager.getInstance();
        CacheFactory cacheFactory;
        try {
            cacheFactory = cacheManager.getCacheFactory();
            cache = cacheFactory.createCache(Collections.EMPTY_MAP);
        }
        catch (CacheException e) {
            throw new RuntimeException(e);
        }
    }

    public void invalidate() {
        cache.clear();
    }

    @SuppressWarnings("unchecked")
    public <T> T cache(CacheProducer<T> producer, Serializable... params) {
        CacheKey key = new CacheKey(params);
        if (cache.containsKey(key)) {
            return (T) cache.get(key);
        }
        T t = producer.produce();
        cache.put(key, t);
        return t;
    }

    public void remove(Serializable... params) {
        cache.remove(new CacheKey(params));
    }
}
