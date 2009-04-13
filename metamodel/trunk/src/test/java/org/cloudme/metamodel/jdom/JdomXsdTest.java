package org.cloudme.metamodel.jdom;

import static org.cloudme.metamodel.jdom.JdomTestHelper.createXsdDoc;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.cloudme.metamodel.AbstractXsdTest;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.transform.JDOMSource;
import org.junit.Test;
import org.xml.sax.SAXException;

public class JdomXsdTest extends AbstractXsdTest {
    private static final Namespace NS_META = Namespace.getNamespace("", "http://cloudme.org/metamodel");

    @Test
    public void testWithJdom() throws Exception {
        Document xmlDoc = createXmlDoc();
        prettyPrint(xmlDoc);
        Document xsdDoc = createXsdDoc();
        prettyPrint(xsdDoc);

        assertXsdValid(new StreamSource(new ByteArrayInputStream(new XMLOutputter().outputString(xsdDoc).getBytes())), new JDOMSource(xmlDoc));
        assertXsdValid(new JDOMSource(xsdDoc), new JDOMSource(xmlDoc));
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
            System.out.println("DEBUG: " + element.getAttributeValue("name"));
        }
        Document xml = createXmlDoc();
        handler = new XPathHandler("/system/*", xml.getRootElement().getNamespace());
        nodes = selectElements(xml, handler);
        assertEquals(3, nodes.size());
        for (Element element : nodes) {
            System.out.println("DEBUG: " + element.getText());
        }
    }
    
    protected void assertXsdValid(Source xsdSource, Source xmlSource) throws SAXException, ParserConfigurationException, IOException {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(xsdSource);
        Validator validator = schema.newValidator();
        validator.validate(xmlSource);
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
