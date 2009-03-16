package org.cloudme.metamodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;

import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Nodes;
import nu.xom.Serializer;
import nu.xom.XPathContext;

import org.junit.Test;
import org.xml.sax.SAXException;


public class XomXsdTest extends AbstractXsdTest {
    private static final String URI_META = "http://cloudme.org/metamodel";
    private static final String URI_XSD = "http://www.w3.org/2001/XMLSchema";

    @Test
    public void testWithXom() throws Exception {
        Document xsdDoc = createXsdDoc();
        prettyPrint(xsdDoc);
        Document xmlDoc = createXmlDoc();
        prettyPrint(xmlDoc);
        
        assertXsdValid(xsdDoc, xmlDoc);
        
        boolean failed = true;
        try {
            xmlDoc.getRootElement().appendChild(new Element("test", URI_META));
            prettyPrint(xmlDoc);
            assertXsdValid(xsdDoc, xmlDoc);
            failed = false;
        } catch (AssertionError e) {
            // expected
        }
        assertTrue(failed);
    }

    @Test
    public void testWithXomInvalidExample() throws Exception {
        Document xsdDoc = createXsdDoc();
        Document xmlDoc = createXmlDoc();
        xmlDoc.getRootElement().appendChild(new Element("test", URI_META));
        prettyPrint(xmlDoc);
        
        boolean failed = true;
        System.out.println("The following error output is expected: -------");
        try {
            assertXsdValid(xsdDoc, xmlDoc);
            failed = false;
        } catch (AssertionError e) {
            // expected
        }
        System.out.println("The previous error output was expected -------");
        assertTrue(failed);
    }
    
    @Test
    public void testXomXpath() {
        Document doc = createXsdDoc();
        Nodes names = doc.query("/xs:schema/xs:element[@name='system']//xs:element", new XPathContext("xs", URI_XSD));
        assertEquals(3, names.size());
    }

    private void assertXsdValid(Document xsdDoc, Document xmlDoc) throws SAXException, ParserConfigurationException, IOException {
        assertXsdValid(new StreamSource(new ByteArrayInputStream(xsdDoc.toXML().getBytes())), new ByteArrayInputStream(xmlDoc.toXML().getBytes()));
    }

    private Document createXmlDoc() {
        Element system = new Element("system", URI_META);
        Element name = new Element("name", URI_META);
        name.appendChild("Lightroom");
        system.appendChild(name);
        Element vendor = new Element("vendor", URI_META);
        vendor.appendChild("Adobe");
        system.appendChild(vendor);
        Element version = new Element("version", URI_META);
        version.appendChild("1");
        system.appendChild(version);
        Document doc = new Document(system);
        return doc;
    }

    private void prettyPrint(Document doc) throws Exception {
        Serializer serializer = new Serializer(System.out);
        serializer.setIndent(2);
        serializer.write(doc);
    }

    private Document createXsdDoc() {
        Element root = new Element("xs:schema", URI_XSD);
        root.addAttribute(new Attribute("targetNamespace", URI_META));
        root.addAttribute(new Attribute("elementFormDefault", "qualified"));
        Element system = new Element("xs:element", URI_XSD);
        system.addAttribute(new Attribute("name", "system"));
        Element complexType = new Element("xs:complexType", URI_XSD);
        Element sequence = new Element("xs:sequence", URI_XSD);
        Element name = new Element("xs:element", URI_XSD);
        name.addAttribute(new Attribute("name", "name"));
        name.addAttribute(new Attribute("type", "xs:string"));
        sequence.appendChild(name);
        Element vendor = new Element("xs:element", URI_XSD);
        vendor.addAttribute(new Attribute("name", "vendor"));
        vendor.addAttribute(new Attribute("type", "xs:string"));
        sequence.appendChild(vendor);
        Element version = new Element("xs:element", URI_XSD);
        version.addAttribute(new Attribute("name", "version"));
        version.addAttribute(new Attribute("type", "xs:decimal"));
        sequence.appendChild(version);
        complexType.appendChild(sequence);
        system.appendChild(complexType);
        root.appendChild(system);
        Document doc = new Document(root);
        return doc;
    }
}
