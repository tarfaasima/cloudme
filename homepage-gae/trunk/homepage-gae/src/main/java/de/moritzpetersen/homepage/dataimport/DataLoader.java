package de.moritzpetersen.homepage.dataimport;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.inject.Inject;

import de.moritzpetersen.homepage.domain.Entry;
import de.moritzpetersen.homepage.util.DateUtil;

public class DataLoader {
    private static final Logger LOGGER = Logger.getLogger(DataLoader.class.getName());
    @Inject
    private EntryHandler entryHandler;
    @Inject
    private SourceHandler sourceHandler;

    public void load(InputStream in) {
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(in, new DefaultHandler() {
                private final DateFormat dateFormat = DateUtil.getDateTimeFormat();
                private Entry entry;
                private StringBuilder chars;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes)
                        throws SAXException {
                    if ("h_entry".equals(qName)) {
                        entry = new Entry();
                    }
                    chars = new StringBuilder();
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    chars.append(ch, start, length);
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if ("h_entry".equals(qName)) {
                        entryHandler.handle(entry);
                        return;
                    }
                    String value = chars.toString().trim();
                    if ("id".equals(qName)) {
                        entry.setOldId(value);
                    }
                    else if ("title".equals(qName)) {
                        entry.setTitle(value);
                    }
                    else if ("content".equals(qName)) {
                        entry.setContent(value);
                    }
                    else if ("url".equals(qName)) {
                        entry.setUrl(value);
                    }
                    else if ("date".equals(qName)) {
                        try {
                            entry.setDate(dateFormat.parse(value));
                        }
                        catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else if ("origin".equals(qName)) {
                        entry.setSourceId(sourceHandler.resolve(value));
                    }
                }
            });
        }
        catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SAXParseException e) {
            if ("Content is not allowed in prolog.".equals(e.getMessage())) {
                LOGGER.info("Invalid data.");
            }
            else {
                LOGGER.log(Level.WARNING, "Error while parsing data.", e);
            }
        }
        catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
