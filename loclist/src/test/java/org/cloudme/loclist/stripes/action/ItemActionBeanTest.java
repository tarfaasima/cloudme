package org.cloudme.loclist.stripes.action;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.cloudme.loclist.model.ItemInstance;
import org.cloudme.loclist.test.AbstractServiceTestCase;
import org.junit.Test;

public class ItemActionBeanTest extends AbstractServiceTestCase {
    @Test
    public void testDelete() throws Exception {
        createItems("A", "B", "C");
        createItemList("L", "A");

        String url = "/action/edit/" + itemList("L").getId();
        EditActionBean bean = createActionBean(url, EditActionBean.class);

        Iterator<ItemInstance> it = bean.getItemInstances().iterator();
        assertTrue(it.next().isInList());
        assertFalse(it.next().isInList());
        assertFalse(it.next().isInList());
        assertFalse(it.hasNext());
    }
}
