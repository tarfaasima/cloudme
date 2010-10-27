package org.cloudme.loclist.stripes.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.cloudme.loclist.model.ItemList;
import org.cloudme.loclist.test.AbstractServiceTestCase;
import org.junit.Test;

public class ListActionBeanTest extends AbstractServiceTestCase {
    @Test
    public void testShow() throws Exception {
        createItemList("Test");
        String url = "/action/list/show/" + itemList("Test").getId();
        ListActionBean bean = createActionBean(url, ListActionBean.class);
        assertEquals("Test", bean.getItemList().getName());
    }

    @Test
    public void testIndex() throws Exception {
        createItemList("Test 1");
        createItemList("Test 2");
        String url = "/action/list/";
        ListActionBean bean = createActionBean(url, ListActionBean.class);
        assertEquals(2, bean.getItemLists().size());
    }

    @Test
    public void testSave() throws Exception {
        ItemList itemList = new ItemList();
        itemList.setName("Test");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("itemList", itemList);

        String url = "/action/list/save";
        createActionBean(url, ListActionBean.class, params);

        assertNotNull(itemList("Test"));
    }

    @Test
    public void testDelete() throws Exception {
        createItems("Foo", "Bar", "XYZ");
        createItemList("Test", "Foo", "XYZ");
        assertNotNull(itemList("Test"));

        String url = "/action/list/delete/" + itemList("Test").getId();
        createActionBean(url, ListActionBean.class);

        assertNull(itemList("Test"));
        assertNull(itemInstance("Foo"));
        assertNull(itemInstance("XYZ"));
        assertNotNull(itemInstance("Bar"));
    }
}
