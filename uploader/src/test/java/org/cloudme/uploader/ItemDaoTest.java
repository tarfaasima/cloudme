package org.cloudme.uploader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.cloudme.sugar.AbstractServiceTestCase;
import org.junit.Test;

import com.google.inject.Inject;
import com.google.inject.Module;

public class ItemDaoTest extends AbstractServiceTestCase {
    @Inject
    private ItemDao dao;

    @Test
    public void testItemDao() {
        Item item = new Item();
        item.setContentType("text/plain");
        item.setData("Hello World".getBytes());
        item.setName("noname.txt");
        dao.put(item);

        assertTrue(item.getId() > 0);

        assertEquals("Hello World", new String(dao.find(item.getId()).getData()));
    }

    @Override
    protected Module[] getModules() {
        return new Module[] { new UploaderModule() };
    }
}
