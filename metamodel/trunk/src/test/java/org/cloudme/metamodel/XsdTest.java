package org.cloudme.metamodel;

import java.io.ByteArrayInputStream;
import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XsdTest {
    @Test
    public void testXsd() throws Exception {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        ErrorHandler myErrorHandler = new ErrorHandler() {
            public void warning(SAXParseException exception) throws SAXException {
                System.out.println("WARNING: " + exception.toString());
            }

            public void fatalError(SAXParseException exception) throws SAXException {
                System.out.println("FATAL: " + exception.toString());
            }

            public void error(SAXParseException exception) throws SAXException {
                System.out.println("ERROR: " + exception.toString());
            }
        };
        sf.setErrorHandler(myErrorHandler);
        // read to memory
        String schemaStr = FileUtils.readFileToString(new File("metamodel.xsd"));
        System.out.println("DEBUG: " + schemaStr);
        // get schema from memory
        Schema schema = sf.newSchema(new StreamSource(new ByteArrayInputStream(schemaStr.getBytes())));
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setSchema(schema);
        dbf.setValidating(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        db.setErrorHandler(myErrorHandler);
        db.parse(new File("metamodel.xml"));
    }
}
