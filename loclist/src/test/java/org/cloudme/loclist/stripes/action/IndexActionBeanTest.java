package org.cloudme.loclist.stripes.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.cloudme.loclist.model.Note;
import org.cloudme.loclist.test.AbstractServiceTestCase;
import org.junit.Test;

public class IndexActionBeanTest extends AbstractServiceTestCase {
    @Test
    public void testIndex() throws Exception {
        createNote("Test 1");
        createNote("Test 2");
        String url = "/action/index/";
        IndexActionBean bean = createActionBean(url, IndexActionBean.class);
        assertEquals(2, bean.getNotes().size());
    }

    // @Test
    public void testSave() throws Exception {
        Note note = new Note();
        note.setName("Test");

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
        // IndexActionBean.class);
        // trip.getRequest().setAttribute("note", note);
        // trip.execute("save");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("itemList", note);

        String url = "/action/index/save";
        createActionBean(url, IndexActionBean.class, params);

        assertNotNull(note("Test"));
    }

    // @Test
    public void testDelete() throws Exception {
        createItems("Foo", "Bar", "XYZ");
        createNote("Test", "Foo", "XYZ");
        createNote("Test 2", "Bar");
        assertNotNull(note("Test"));

        String url = "/action/index/delete/" + note("Test").getId();
        createActionBean(url, IndexActionBean.class);

        refresh();

        assertNull(note("Test"));
        assertNull(noteItem("Foo"));
        assertNull(noteItem("XYZ"));
        assertNotNull(noteItem("Bar"));
    }
}
