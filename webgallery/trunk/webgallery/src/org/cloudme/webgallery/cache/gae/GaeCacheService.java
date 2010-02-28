package org.cloudme.webgallery.cache.gae;

import java.io.Serializable;
import java.util.Collections;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;

import org.cloudme.webgallery.cache.CacheProducer;
import org.cloudme.webgallery.cache.CacheService;
import org.springframework.stereotype.Component;

@Component
public class GaeCacheService implements CacheService {
    private final Cache cache;

    public GaeCacheService() throws CacheException {
        CacheManager cacheManager = CacheManager.getInstance();
        CacheFactory cacheFactory = cacheManager.getCacheFactory();
        cache = cacheFactory.createCache(Collections.EMPTY_MAP);
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

    @SuppressWarnings("unchecked")
    public <T> void update(T data, Serializable... params) {
        cache.put(new CacheKey(params), data);
    }
}
