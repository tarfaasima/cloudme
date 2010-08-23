package de.moritzpetersen.homepage.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MapXmlParser {
    private final Map<String, String> values = new HashMap<String, String>();
    private String root;

    public void parse(InputStream in) {
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(in, new DefaultHandler() {
                private final StringBuilder keyBuilder = new StringBuilder();

                @Override
                public void startElement(String uri,
                        String localName,
                        String qName,
                        Attributes attributes) throws SAXException {
                    if (root == null) {
                        root = qName;
                    }
                    keyBuilder.append("/").append(qName);
                    for (int i = 0, length = attributes.getLength(); i < length; i++) {
                        values.put(keyBuilder + "@" + attributes.getQName(i),
                                attributes.getValue(i));
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length)
                        throws SAXException {
                    StringBuilder builder = new StringBuilder();
                    String key = keyBuilder.toString();
                    if (values.containsKey(key)) {
                        builder.append(values.get(key));
                    }
                    builder.append(ch, start, length);
                    values.put(key, builder.toString());
                }

                @Override
                public void endElement(String uri,
                        String localName,
                        String qName) throws SAXException {
                    int end = keyBuilder.length();
                    int start = end - qName.length() - 1;
                    if (start >= 0) {
                        keyBuilder.delete(start, end);
                    }
                }
            });
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (SAXException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String key) {
        return values.get(key);
    }

    public String getRoot() {
        return root;
    }
}
