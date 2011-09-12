package org.cloudme.uploader;

import com.google.inject.AbstractModule;

final class UploaderModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ItemDao.class);
		bind(ItemService.class);
    }
}