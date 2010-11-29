package org.cloudme.loclist.stripes.action;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.cloudme.loclist.model.ItemInstance;
import org.cloudme.loclist.test.AbstractServiceTestCase;
import org.junit.Test;

public class EditActionBeanTest extends AbstractServiceTestCase {
    @Test
    public void testDefault() throws Exception {
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

    @Test
    public void testDelete() throws Exception {
        createItems("A", "B", "C");
        createItemList("L", "A");

        String url = String.format("/action/edit/%d/delete/%d", itemList("L").getId(), item("A").getId());
        EditActionBean bean = createActionBean(url, EditActionBean.class);

        refresh();
        assertNull(item("A"));
        assertNull(itemInstance("A"));
    }
}
