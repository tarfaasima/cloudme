package org.cloudme.uploader.cache;

import java.io.Serializable;
import java.util.Collections;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;

class CacheServiceImpl implements CacheService {
	private Cache cache;

	@SuppressWarnings("unchecked")
	@Override
	public <T> T cache(CacheProducer<T> producer, Serializable... params) {
		Long id = (Long) params[0];
		try {
			Cache cache = getCacheInstance();
			T value = (T) cache.get(id);
			if (value == null) {
				value = producer.produce();
				cache.put(id, value);
			}
			return value;
		} catch (CacheException e) {
			return producer.produce();
		}
	}

	private Cache getCacheInstance() throws CacheException {
		if (cache == null) {
            cache = CacheManager.getInstance().getCacheFactory().createCache(Collections.EMPTY_MAP);
		}
		return cache;
	}

	@Override
	public void invalidate() {
		try {
			getCacheInstance().evict();
		} catch (CacheException e) {
			// no cache created - nothing to invalidate.
		}
	}
}
