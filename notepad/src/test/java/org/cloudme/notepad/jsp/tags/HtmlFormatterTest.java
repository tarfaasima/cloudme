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
        assertFormat("&lt;&amp;&quot;&gt;", "<&\">");
        assertFormat("Hello<br>World!", "Hello\nWorld!");
        assertFormat("Hello<p>World!", "Hello\n\nWorld!");
        assertFormat("Fu&#223;ball", "Fußball");
        assertFormat("<ul><li>Hello World</ul>", "- Hello World");
        assertFormat("<ul><li>Hello World<li>Hello World</ul>", "- Hello World\n- Hello World");
        assertFormat("This is a test<ul><li>Hello World<li>Hello World</ul>",
                "This is a test\n\n- Hello World\n- Hello World");
        assertFormat("This is a test<ul><li>Hello World<li>Hello World</ul>or not",
                "This is a test\n\n- Hello World\n- Hello World\n\nor not");
        assertFormat("This &rarr; is a test.", "This --> is a test.");
        assertFormat("&rarr; is a test.", "--> is a test.");
        assertFormat("This &rarr;", "This -->");
        assertFormat("This &larr; is a test.", "This <-- is a test.");
        assertFormat("&larr; is a test.", "<-- is a test.");
        assertFormat("This &larr;", "This <--");
        // assertFormat("This is <a href=\"http://www.example.com/test/me\">http://www.example.&hellip;</a> a test.",
        // "This is http://www.example.com/test/me a test.");
        // assertFormat("This is <a href=\"http://www.example.com/test/me\">http://www.example.&hellip;</a>",
        // "This is http://www.example.com/test/me");
        assertFormat("This is <em>another</em> test.", "This is *another* test.");
        assertFormat("This is *not a test.", "This is *not a test.");
    }

    private void assertFormat(String expected, String input) {
        assertEquals(expected, new HtmlFormatter().format(input));
    }

}
