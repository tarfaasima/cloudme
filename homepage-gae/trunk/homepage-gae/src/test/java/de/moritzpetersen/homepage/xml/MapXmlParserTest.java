package de.moritzpetersen.homepage.xml;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;

import org.junit.Test;


public class MapXmlParserTest {
    @Test
    public void testXmlParsing() {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"
                + "<foo>"
                + "<bar attr=\"123\">Hello World!</bar>"
                + "</foo>";
        MapXmlParser parser = new MapXmlParser();
        parser.parse(new ByteArrayInputStream(xml.getBytes()));
        assertEquals("foo", parser.getRoot());
        assertEquals("123", parser.get("/" + parser.getRoot() + "/bar@attr"));
    }
}
