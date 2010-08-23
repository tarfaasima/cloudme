package de.moritzpetersen.homepage.dataimport;

import org.junit.Before;
import org.junit.Test;


public class DataImporterTest {
    private static final String XML_DATA = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"
            + "<db123>"
            + "<h_entry>"
            + "<id>1</id>"
            + "<title>This is the first title</title>"
            + "<content>This is the first content</content>"
            + "<url>http://test.url.com/something</url>"
            + "<date>2009-07-10 13:07:46</date>"
            + "<origin>http://test.url.com</origin>"
            + "</h_entry>"
            + "</db123>";

    @Before
    public void setUp() {

    }

    @Test
    public void testImportData() {
        DataImporter importer = new DataImporter(XML_DATA);
        EntryHandler entryHandler = new EntryHandler() {
        };
        SourceHandler sourceHandler = new SourceHandler() {
        };
    }
}
