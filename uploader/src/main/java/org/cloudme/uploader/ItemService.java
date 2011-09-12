package org.cloudme.uploader;

import org.cloudme.sugar.AbstractService;
import org.cloudme.uploader.cache.CacheProducer;
import org.cloudme.uploader.cache.CacheService;

import com.google.inject.Inject;

public class ItemService extends AbstractService<Item> {
	@Inject
	private CacheService cacheService;
	private final ItemDao dao;

	@Inject
	public ItemService(ItemDao dao) {
		super(dao);
		this.dao = dao;
	}

	@Override
	public Item find(final Long id) {
		return cacheService.cache(new CacheProducer<Item>() {
			@Override
			public Item produce() {
				return dao.find(id);
			}
		}, id);
	}
}
