package org.cloudme.notepad.jsp.tags;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HtmlFormatterTest {

    @Test
    public void testFormat() {
        for (int i = 0; i < 256; i++) {
            System.out.println(i + "(0x" + Integer.toHexString(i) + "):" + (char) i);
        }

        assertFormat("Hello World!", "Hello World!");
    }

    private void assertFormat(String expected, String input) {
        assertEquals(expected, new HtmlFormatter().format(input));
    }

}
