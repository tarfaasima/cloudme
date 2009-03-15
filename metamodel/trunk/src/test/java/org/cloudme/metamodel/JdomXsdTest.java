package org.cloudme.metamodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.transform.JDOMSource;
import org.jdom.xpath.XPath;
import org.junit.Test;
import org.xml.sax.InputSource;

public class JdomXsdTest extends AbstractXsdTest {
    private static final Namespace NS_META = Namespace.getNamespace("", "http://cloudme.org/metamodel");
    private static final Namespace NS_XSD = Namespace.getNamespace("xs", "http://www.w3.org/2001/XMLSchema");

    @Test
    public void testWithJdom() throws Exception {
        Document xmlDoc = createXmlDoc();
        prettyPrint(xmlDoc);
        Document xsdDoc = createXsdDoc();
        prettyPrint(xsdDoc);

        assertXsdValid(new JDOMSource(xsdDoc), inputSource(xmlDoc));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testJdomXpath() throws Exception {
        Document xsd = createXsdDoc();
        Namespace namespace = xsd.getRootElement().getNamespace();
        XPath xpath = XPath.newInstance(replacePrefix(namespace.getPrefix(), "/xs:schema/xs:element[@name='system']//xs:element"));
        xpath.addNamespace(namespace);
        List<? extends Content> nodes = (List<? extends Content>) xpath.selectNodes(xsd);
        assertEquals(3, nodes.size());
        for (Content content : nodes) {
            if (content instanceof Element) {
                Element element = (Element) content;
                System.out.println(element.getAttributeValue("name"));
            }
            else {
                fail("All selected nodes should be elements.");
            }
        }
        Document xml = createXmlDoc();
        xpath = XPath.newInstance("/system/*");
        xpath.addNamespace(xml.getRootElement().getNamespace());
        nodes = xpath.selectNodes(xml);
        assertEquals(3, nodes.size());
        for (Content content : nodes) {
            if (content instanceof Element) {
                Element element = (Element) content;
                System.out.println(element.getText());
            }
            else {
                fail("All selected nodes should be elements.");
            }
        }
    }

    private String replacePrefix(String prefix, String xpathQuery) {
        return "xs".equals(prefix) ? xpathQuery : xpathQuery.replace("xs:", prefix + ":");
    }

    private InputSource inputSource(Document xmlDoc) {
        return new JDOMSource(xmlDoc).getInputSource();
    }

    private void prettyPrint(Document xmlDoc) throws Exception {
        new XMLOutputter(Format.getPrettyFormat()).output(xmlDoc, System.out);
    }

    private Document createXmlDoc() {
        Document doc = new Document();
        Element root = new Element("system", NS_META);
        root.addContent(new Element("name", root.getNamespace()).setText("Lightroom"));
        root.addContent(new Element("vendor", root.getNamespace()).setText("Adobe"));
        root.addContent(new Element("version", root.getNamespace()).setText("1"));
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
