package org.cloudme.webgallery.cache;

import java.io.Serializable;

/**
 * The {@link CacheService} stores data in the cache if not yet cached or loads
 * the data from cache. To dynamically create data, a {@link CacheProducer} is
 * used.
 * 
 * @author <a href="mailto:moritz@cloudme.org">Moritz Petersen</a>
 * @see CacheProducer
 */
public interface CacheService {
    /**
     * Gets the data from cache or puts data in cache if not cached.
     * 
     * @param <T>
     *            The type of data.
     * @param producer
     *            The producer.
     * @param params
     *            The key parameters to look up data in the cache.
     * @return The cached data.
     */
    <T> T cache(CacheProducer<T> producer, Serializable... params);

    /**
     * Invalidats (clears) the cache.
     */
    void invalidate();

    /**
     * Updates the data with the given cache key.
     * 
     * @param <T>
     *            The type of data.
     * @param data
     *            The data.
     * @param params
     *            The key parameters.
     */
    <T> void update(T data, Serializable... params);
}
