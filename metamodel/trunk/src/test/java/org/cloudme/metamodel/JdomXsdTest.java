package org.cloudme.metamodel;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.transform.JDOMSource;
import org.junit.Test;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class JdomXsdTest {
    private boolean isValid = true;

    private ErrorHandler myErrorHandler = new ErrorHandler() {
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

    private static final Namespace NS_META = Namespace.getNamespace("m", "http://cloudme.org/metamodel");
    private static final Namespace NS_XSD = Namespace.getNamespace("xs", "http://www.w3.org/2001/XMLSchema");

    @Test
    public void testWithJdom() throws Exception {
        Document xmlDoc = createXmlDoc();
        prettyPrint(xmlDoc);
        Document xsdDoc = createXsdDoc();
        prettyPrint(xsdDoc);

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        sf.setErrorHandler(myErrorHandler);
        Schema schema = sf.newSchema(new JDOMSource(xsdDoc));
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setSchema(schema);
        DocumentBuilder db = dbf.newDocumentBuilder();
        db.setErrorHandler(myErrorHandler);
        db.parse(new ByteArrayInputStream(new XMLOutputter().outputString(xmlDoc).getBytes()));

        assertTrue(isValid);
    }

    private void prettyPrint(Document xmlDoc) throws Exception {
        new XMLOutputter(Format.getPrettyFormat()).output(xmlDoc, System.out);
    }

    private Document createXmlDoc() {
        Document doc = new Document();
        Element root = new Element("system", NS_META);
        // root.addNamespaceDeclaration(NS_XSI);
        // root.setAttribute("schemaLocation",
        // "http://cloudme.org/metamodel metamodel.xsd", NS_XSI);
        root.addContent(new Element("name", NS_META).setText("Lightroom"));
        root.addContent(new Element("vendor", NS_META).setText("Adobe"));
        root.addContent(new Element("version", NS_META).setText("1"));
        doc.setRootElement(root);
        return doc;
    }

    private Document createXsdDoc() {
        Document doc = new Document();
        Element root = new Element("schema", NS_XSD);
        root.setAttribute("targetNamespace", "http://cloudme.org/metamodel");
        root.setAttribute("elementFormDefault", "qualified");
        root.addContent(new Element("element", NS_XSD).setAttribute("name", "system").addContent(new Element("complexType", NS_XSD).addContent(new Element("sequence", NS_XSD).addContent(Arrays.asList(new Element("element", NS_XSD).setAttribute("name", "name").setAttribute("type", "xs:string"), new Element("element", NS_XSD).setAttribute("name", "vendor").setAttribute("type", "xs:string"), new Element("element", NS_XSD).setAttribute("name", "version").setAttribute("type", "xs:decimal"))))));
        doc.setRootElement(root);
        return doc;
    }
}
