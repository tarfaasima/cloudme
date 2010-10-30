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

    // @Test
    public void testSave() throws Exception {
        ItemList itemList = new ItemList();
        itemList.setName("Test");

        // MockServletContext context = new MockServletContext("test");
        // Map<String, String> filterParams = new HashMap<String, String>();
        // filterParams.put("ActionResolver.Packages",
        // "net.sourceforge.stripes");
        // context.addFilter(StripesFilter.class, "StripesFilter",
        // filterParams);
        // context.setServlet(DispatcherServlet.class, "StripesDispatcher",
        // null);
        //
        // MockRoundtrip trip = new MockRoundtrip(context,
        // ListActionBean.class);
        // trip.getRequest().setAttribute("itemList", itemList);
        // trip.execute("save");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("itemList", itemList);

        String url = "/action/list/save";
        createActionBean(url, ListActionBean.class, params);

        assertNotNull(itemList("Test"));
    }

    // @Test
    public void testDelete() throws Exception {
        createItems("Foo", "Bar", "XYZ");
        createItemList("Test", "Foo", "XYZ");
        createItemList("Test 2", "Bar");
        assertNotNull(itemList("Test"));

        String url = "/action/list/delete/" + itemList("Test").getId();
        createActionBean(url, ListActionBean.class);

        refreshItemInstances();

        assertNull(itemList("Test"));
        assertNull(itemInstance("Foo"));
        assertNull(itemInstance("XYZ"));
        assertNotNull(itemInstance("Bar"));
    }
}
