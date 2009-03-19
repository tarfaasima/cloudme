package org.cloudme.metamodel.jdom;

import static org.cloudme.metamodel.jdom.JdomTestHelper.NS_XSD;
import static org.cloudme.metamodel.jdom.JdomTestHelper.createXsdDoc;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.junit.Test;

public class XPathHandlerTest {
    @SuppressWarnings("unchecked")
    @Test
    public void testSelectNodes() {
        Document doc = createXsdDoc();
        String xpath = "/xs:schema/xs:element";
        List<Element> nodes = new XPathHandler(xpath, NS_XSD).selectNodes(doc);
        assertEquals("One element below root node expected.", 1, nodes.size());
        
        Element element = nodes.get(0);
        xpath = "descendant::xs:element";
        nodes = new XPathHandler(xpath, NS_XSD).selectNodes(element);
        assertEquals("Three elements below selected node expected.", 3, nodes.size());
        
        String[] names = {"name", "vendor", "version"};
        for (int i = 0; i < names.length; i++) {
            assertEquals(names[i], nodes.get(i).getAttributeValue("name"));
        }
        
        xpath = "descendant::xs:element[@name='version']";
        nodes = new XPathHandler(xpath, NS_XSD).selectNodes(element);
        assertEquals("One node with name 'version' expected.", 1, nodes.size());
        
        xpath = "descendant::xs:element[@name='unknown']";
        nodes = new XPathHandler(xpath, NS_XSD).selectNodes(element);
        assertEquals("No node with name 'unknown' expected.", 0, nodes.size());
        
        xpath = "descendant::xs:element[@name='version']";
        Object node = new XPathHandler(xpath, NS_XSD).selectSingleNode(element);
        assertNotNull("One node with name 'version' expected.", node);
        
        xpath = "descendant::xs:element[@name='unknown']";
        node = new XPathHandler(xpath, NS_XSD).selectSingleNode(element);
        assertNull("No node with name 'unknown' expected.", node);
    }
}
