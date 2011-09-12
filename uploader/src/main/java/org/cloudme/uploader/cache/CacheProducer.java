package org.cloudme.uploader.cache;

public interface CacheProducer<T> {
	T produce();
}
