package org.cloudme.metamodel;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class Dom4jXsdTest {
    private boolean noErrors = true;

    private ErrorHandler myErrorHandler = new ErrorHandler() {
        public void warning(SAXParseException exception) throws SAXException {
            System.out.println("WARNING: " + exception.toString());
            noErrors = false;
        }

        public void fatalError(SAXParseException exception) throws SAXException {
            System.out.println("FATAL: " + exception.toString());
            noErrors = false;
        }

        public void error(SAXParseException exception) throws SAXException {
            System.out.println("ERROR: " + exception.toString());
            exception.printStackTrace();
            noErrors = false;
        }

    };

    @Before
    public void setUp() {
        noErrors = true;
    }

    @Test
    public void testWithDom4j() throws Exception {
        Document xmlDoc = createXmlDoc();
        prettyPrint(xmlDoc);
        Document xsdDoc = createXsdDoc();
        prettyPrint(xsdDoc);

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        sf.setErrorHandler(myErrorHandler);
        Schema schema = sf.newSchema(new DocumentSource(xsdDoc));
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setSchema(schema);
//         dbf.setValidating(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        db.setErrorHandler(myErrorHandler);
        db.parse(new ByteArrayInputStream(xmlDoc.asXML().getBytes()));

        assertTrue(noErrors);
    }

    private void prettyPrint(Document doc) throws IOException, UnsupportedEncodingException {
        new XMLWriter(System.out, OutputFormat.createPrettyPrint()).write(doc);
    }

    private Document createXmlDoc() throws Exception {
        // if (false) return new SAXReader().read("metamodel.xml");
        Document doc = DocumentFactory.getInstance().createDocument();
        Element system = doc.addElement("meta:system");
        system.addNamespace("meta", "http://cloudme.org/metamodel");
//        system.addNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
//        system.addAttribute("xsi:schemaLocation", "http://cloudme.org/metamodel metamodel.xsd");
        system.addElement("meta:name").addText("Lightroom");
        system.addElement("meta:vendor").addText("Adobe");
        system.addElement("meta:version").addText("1");
        return doc;
    }

    private Document createXsdDoc() throws Exception {
        // if (false) return new SAXReader().read("metamodel.xsd");
        Document doc = DocumentFactory.getInstance().createDocument();
        Element root = doc.addElement("xsd:schema");
        root.addNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
//        root.addNamespace("meta", "http://cloudme.org/metamodel");
        root.addAttribute("targetNamespace", "http://cloudme.org/metamodel");
        root.addAttribute("elementFormDefault", "qualified");
        Element system = root.addElement("xsd:element");
        system.addAttribute("name", "system");
        Element sequence = system.addElement("xsd:complexType").addElement("xsd:sequence");
        sequence.addElement("xsd:element").addAttribute("name", "name").addAttribute("type", "xsd:string");
        sequence.addElement("xsd:element").addAttribute("name", "vendor").addAttribute("type", "xsd:string");
        sequence.addElement("xsd:element").addAttribute("name", "version").addAttribute("type", "xsd:decimal");
        if (true) {
            return new SAXReader().read(new ByteArrayInputStream(doc.asXML().getBytes()));
        }
        return doc;
    }
}
