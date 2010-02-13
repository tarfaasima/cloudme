package org.cloudme.webgallery.cache.gae;

import java.util.Collections;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;

import org.cloudme.webgallery.cache.CacheKey;
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
    public <T> T cache(CacheKey key, CacheProducer<T> producer) {
        if (cache.containsKey(key)) {
            return (T) cache.get(key);
        }
        T t = producer.produce();
        cache.put(key, t);
        return t;
    }
}
