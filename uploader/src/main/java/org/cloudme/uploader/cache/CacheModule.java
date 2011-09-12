package org.cloudme.uploader.cache;

import com.google.inject.AbstractModule;

public class CacheModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(CacheService.class).to(CacheServiceImpl.class);
	}
}
