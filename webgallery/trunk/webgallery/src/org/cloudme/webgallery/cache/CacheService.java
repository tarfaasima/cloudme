package org.cloudme.webgallery.cache;


public interface CacheService {
    <T> T cache(CacheKey key, CacheProducer<T> producer);

    void invalidate();
}
