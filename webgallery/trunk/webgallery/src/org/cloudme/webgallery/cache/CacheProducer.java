package org.cloudme.webgallery.cache;

public interface CacheProducer<T> {
    T produce();
}
