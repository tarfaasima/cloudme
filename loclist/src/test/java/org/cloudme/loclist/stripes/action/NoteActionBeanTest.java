package org.cloudme.loclist.stripes.action;

import static org.junit.Assert.assertEquals;

import org.cloudme.loclist.test.BaseTestCase;
import org.junit.Before;
import org.junit.Test;


public class NoteActionBeanTest extends BaseTestCase {
    @Before
    public void generateTestData() {
        createItems("Bread", "Apple", "Cheese");
        createNote("Test 1", "Apple", "Bread");
        createNote("Test 2", "Cheese");
    }

    @Test
    public void testCheckin() throws Exception {
        String url = "/action/note/checkin/" + note("Test 1").getId() + "/53.486748985000006/10.21923223";

        NoteActionBean bean = createActionBean(url, NoteActionBean.class);

        assertEquals(2, bean.getNoteItems().size());
    }
}
