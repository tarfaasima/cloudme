package org.cloudme.webgallery.cache;

/**
 * Produces data that is to be cached. It is used by the {@link CacheService} to
 * produce data that is not yet in the cache.
 * 
 * <code><pre>
 *  String s = cacheService.get(new CacheProducer<String>() {
 *      public String produce() {
 *          return "Hello world";
 *      }
 *  }, "key");
 * </pre></code>
 * 
 * @author <a href="mailto:moritz@cloudme.org">Moritz Petersen</a>
 * 
 * @param <T>
 *            The produced data.
 */
public interface CacheProducer<T> {
    /**
     * Produces the data.
     * 
     * @return data.
     */
    T produce();
}
