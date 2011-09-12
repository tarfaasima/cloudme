package org.cloudme.uploader.cache;

import java.io.Serializable;

public interface CacheService {
	<T> T cache(CacheProducer<T> producer, Serializable... params);

	void invalidate();
}
