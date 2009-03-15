package org.cloudme.metamodel;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.Before;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public abstract class AbstractXsdTest {
    private boolean isValid = true;

    protected ErrorHandler myErrorHandler = new ErrorHandler() {
        public void warning(SAXParseException exception) throws SAXException {
            System.out.println("WARNING: " + exception.toString());
            isValid = false;
        }

        public void fatalError(SAXParseException exception) throws SAXException {
            System.out.println("FATAL: " + exception.toString());
            isValid = false;
        }

        public void error(SAXParseException exception) throws SAXException {
            System.out.println("ERROR: " + exception.toString());
            isValid = false;
        }

    };
    
    @Before
    public void setup() {
        isValid = true;
    }
    
    protected void assertXsdValid(Source xsdSource, InputStream xmlInputStream) throws SAXException, ParserConfigurationException, IOException {
        assertXsdValid(xsdSource, new InputSource(xmlInputStream));
    }
    
    protected void assertXsdValid(Source xsdSource, InputSource xmlInputSource) throws SAXException, ParserConfigurationException, IOException {
        isValid = true;
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        sf.setErrorHandler(myErrorHandler);
        Schema schema = sf.newSchema(xsdSource);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setSchema(schema);
        DocumentBuilder db = dbf.newDocumentBuilder();
        db.setErrorHandler(myErrorHandler);
        db.parse(xmlInputSource);
        assertTrue(isValid);
    }
}
