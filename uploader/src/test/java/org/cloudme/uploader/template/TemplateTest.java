package org.cloudme.uploader.template;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TemplateTest {
    @Test
    public void testReplace() {
        Template template = new Template("Hello $name$!");
        template.replace("name", "World");
        assertEquals("Hello World!", template.toString());
    }
}
