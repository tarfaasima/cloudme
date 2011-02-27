package org.cloudme.loclist.stripes.action;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.cloudme.loclist.model.NoteItem;
import org.cloudme.loclist.test.AbstractServiceTestCase;
import org.junit.Test;

public class EditActionBeanTest extends AbstractServiceTestCase {
    @Test
    public void testDefault() throws Exception {
        createItems("A", "B", "C");
        createNote("L", "A");

        String url = "/action/edit/" + note("L").getId();
        EditActionBean bean = createActionBean(url, EditActionBean.class);

        Iterator<NoteItem> it = bean.getNoteItems().iterator();
        assertTrue(it.next().isInNote());
        assertFalse(it.next().isInNote());
        assertFalse(it.next().isInNote());
        assertFalse(it.hasNext());
    }

    @Test
    public void testDelete() throws Exception {
        createItems("A", "B", "C");
        createNote("L", "A");

        String url = String.format("/action/edit/%d/delete/%d", note("L").getId(), item("A").getId());
        createActionBean(url, EditActionBean.class);

        refresh();
        assertNull(item("A"));
        assertNull(noteItem("A"));
    }
}
