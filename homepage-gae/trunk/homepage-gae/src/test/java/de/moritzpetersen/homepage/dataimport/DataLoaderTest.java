package de.moritzpetersen.homepage.dataimport;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import de.moritzpetersen.homepage.domain.Entry;
import de.moritzpetersen.homepage.util.DateUtil;

public class DataLoaderTest {
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
            + "<h_entry>"
            + "<id>1</id>"
            + "<title>This is the first title</title>"
            + "<content>This is the first content</content>"
            + "<url>http://test.url.com/something</url>"
            + "<date>2009-07-10 13:07:46</date>"
            + "<origin>http://test.url.com</origin>"
            + "</h_entry>"
            + "</db123>";

    private Collection<Entry> entries = new ArrayList<Entry>();

    private EntryHandler testEntryHandler = new EntryHandler() {
        @Override
        public void handle(Entry entry) {
            entries.add(entry);
        }
    };

    private SourceHandler testSourceHandler = new SourceHandler() {
        @Override
        public Long resolve(String sourceUrl) {
            assertEquals("http://test.url.com", sourceUrl);
            return 1L;
        }
    };

    private Injector injector;

    private class TestModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(EntryHandler.class).toInstance(testEntryHandler);
            bind(SourceHandler.class).toInstance(testSourceHandler);
            bind(DataLoader.class);
        }
    }

    @Before
    public void setUp() {
        injector = Guice.createInjector(new TestModule());
    }

    @Test
    public void testImportData() throws ParseException {
        DataLoader dataImporter = injector.getInstance(DataLoader.class);
        dataImporter.load(new ByteArrayInputStream(XML_DATA.getBytes()));
        assertEquals(2, entries.size());
        Entry entry = entries.iterator().next();
        assertEquals("1", entry.getOldId());
        assertEquals("This is the first title", entry.getTitle());
        assertEquals("This is the first content", entry.getContent());
        assertEquals("http://test.url.com/something", entry.getUrl());
        assertEquals(DateUtil.getDateTimeFormat()
                .parse("2009-07-10 13:07:46"), entry.getDate());
        assertEquals((Long) 1L, entry.getSourceId());
    }
}
