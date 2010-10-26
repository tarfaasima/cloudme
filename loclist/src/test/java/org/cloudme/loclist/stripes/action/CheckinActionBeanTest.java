package org.cloudme.loclist.stripes.action;

import static org.junit.Assert.assertEquals;

import org.cloudme.loclist.test.AbstractServiceTestCase;
import org.junit.Before;
import org.junit.Test;


public class CheckinActionBeanTest extends AbstractServiceTestCase {
    @Before
    public void generateTestData() {
        createItems("Bread", "Apple", "Cheese");
        createItemList("Test 1", "Apple", "Bread");
        createItemList("Test 2", "Cheese");
    }

    @Test
    public void testShow() throws Exception {
        String url = "/action/checkin/" + itemList("Test 1").getId() + "/53.486748985000006/10.21923223";

        // MockServletContext context = new MockServletContext("test");
        // Map<String, String> params = new HashMap<String, String>();
        // params.put("ActionResolver.Packages",
        // "org.cloudme.loclist.stripes.action");
        // params.put("Extension.Packages",
        // "org.cloudme.loclist.stripes.extensions");
        // context.addFilter(StripesFilter.class, "StripesFilter", params);
        // context.setServlet(DispatcherServlet.class, "StripesDispatcher",
        // null);
        //
        // MockRoundtrip roundtrip = new MockRoundtrip(context,
        // CheckinActionBean.class);
        //
        // roundtrip.addParameter("itemListId",
        // String.valueOf(itemList("Test 1").getId()));
        // roundtrip.addParameter("latitude", "10.21923223");
        // roundtrip.addParameter("longitude", "53.486748985000006");
        //
        // roundtrip.execute();

        CheckinActionBean bean = createActionBean(url, CheckinActionBean.class);

        assertEquals(2, bean.getItemInstances().size());
    }
}
