package org.cloudme.metamodel.jdom;

import static org.cloudme.metamodel.jdom.JdomTestHelper.createXsdDoc;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.cloudme.metamodel.AbstractXsdTest;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.transform.JDOMSource;
import org.junit.Test;

public class JdomXsdTest extends AbstractXsdTest {
    private static final Namespace NS_META = Namespace.getNamespace("", "http://cloudme.org/metamodel");

    @Test
    public void testWithJdom() throws Exception {
        Document xmlDoc = createXmlDoc();
        prettyPrint(xmlDoc);
        Document xsdDoc = createXsdDoc();
        prettyPrint(xsdDoc);

        assertXsdValid(new JDOMSource(xsdDoc), new JDOMSource(xmlDoc).getInputSource());
    }

    @Test
    public void testJdomXpath() throws Exception {
        Document xsd = createXsdDoc();
        Namespace namespace = xsd.getRootElement().getNamespace();
        String expr = replacePrefix(namespace.getPrefix(), "/xs:schema/xs:element[@name='system']//xs:element");
        XPathHandler handler = new XPathHandler(expr, namespace);
        List<Element> nodes = selectElements(xsd, handler);
        assertEquals(3, nodes.size());
        for (Element element : nodes) {
            System.out.println(element.getAttributeValue("name"));
        }
        Document xml = createXmlDoc();
        handler = new XPathHandler("/system/*", xml.getRootElement().getNamespace());
        nodes = selectElements(xml, handler);
        assertEquals(3, nodes.size());
        for (Element element : nodes) {
            System.out.println(element.getText());
        }
    }

    @SuppressWarnings("unchecked")
    private List<Element> selectElements(Document xsd, XPathHandler handler) throws JDOMException {
        return handler.selectNodes(xsd);
    }

    private String replacePrefix(String prefix, String xpathQuery) {
        return "xs".equals(prefix) ? xpathQuery : xpathQuery.replace("xs:", prefix + ":");
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
}
