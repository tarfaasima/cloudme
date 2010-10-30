package org.cloudme.loclist.stripes.action;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.cloudme.loclist.model.Item;
import org.cloudme.loclist.test.AbstractServiceTestCase;
import org.junit.Test;


public class ItemActionBeanTest extends AbstractServiceTestCase {
    @Test
    public void testDelete() throws Exception {
        createItems("A", "B", "C");
        createItemList("L", "A");

        String url = "/action/item/" + itemList("L").getId();
        ItemActionBean bean = createActionBean(url, ItemActionBean.class);

        List<Item> items = bean.getItems();
        assertEquals(2, items.size());
        assertEquals("B", items.get(0).getText());
        assertEquals("C", items.get(1).getText());
    }
}
