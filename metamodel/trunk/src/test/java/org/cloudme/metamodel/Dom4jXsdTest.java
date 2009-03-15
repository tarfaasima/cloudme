package org.cloudme.metamodel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

public class Dom4jXsdTest extends AbstractXsdTest {
    @Test
    public void testWithDom4j() throws Exception {
        Document xmlDoc = createXmlDoc();
        prettyPrint(xmlDoc);
        Document xsdDoc = createXsdDoc();
        prettyPrint(xsdDoc);

        assertXsdValid(new DocumentSource(xsdDoc), new ByteArrayInputStream(xmlDoc.asXML().getBytes()));
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
