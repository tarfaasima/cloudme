package org.cloudme.loclist.stripes.action;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.cloudme.loclist.item.ListItem;
import org.cloudme.loclist.test.AbstractServiceTestCase;
import org.junit.Test;

public class ItemActionBeanTest extends AbstractServiceTestCase {
    @Test
    public void testDelete() throws Exception {
        createItems("A", "B", "C");
        createItemList("L", "A");

        String url = "/action/item/" + itemList("L").getId();
        ItemActionBean bean = createActionBean(url, ItemActionBean.class);

        Iterator<ListItem> it = bean.getListItems().iterator();
        assertTrue(it.next().isInList());
        assertFalse(it.next().isInList());
        assertFalse(it.next().isInList());
        assertFalse(it.hasNext());
    }
}
