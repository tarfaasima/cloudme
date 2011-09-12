package org.cloudme.uploader;

import static org.junit.Assert.assertEquals;

import org.cloudme.sugar.AbstractServiceTestCase;
import org.cloudme.uploader.cache.CacheModule;
import org.junit.Test;

import com.google.inject.Inject;
import com.google.inject.Module;

public class ItemServiceTest extends AbstractServiceTestCase {
	@Inject
	private ItemService itemService;

	@Test
	public void testFindLong() {
		Item item = new Item();
		item.setData("Hello World".getBytes());
		itemService.put(item);

		Item item2 = itemService.find(item.getId());
		assertEquals("Hello World", new String(item2.getData()));
	}

	@Override
	protected Module[] getModules() {
		return new Module[] { new UploaderModule(), new CacheModule() };
	}

}
