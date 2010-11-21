package org.cloudme.loclist.stripes.action;

import static org.junit.Assert.assertEquals;

import org.cloudme.loclist.test.AbstractServiceTestCase;
import org.junit.Before;
import org.junit.Test;


public class ListActionBeanTest extends AbstractServiceTestCase {
    @Before
    public void generateTestData() {
        createItems("Bread", "Apple", "Cheese");
        createItemList("Test 1", "Apple", "Bread");
        createItemList("Test 2", "Cheese");
    }

    @Test
    public void testCheckin() throws Exception {
        String url = "/action/list/checkin/" + itemList("Test 1").getId() + "/53.486748985000006/10.21923223";

        ListActionBean bean = createActionBean(url, ListActionBean.class);

        assertEquals(2, bean.getItemInstances().size());
    }
}
