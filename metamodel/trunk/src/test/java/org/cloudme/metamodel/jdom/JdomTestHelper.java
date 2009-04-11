package org.cloudme.metamodel.jdom;

import java.util.Arrays;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

abstract class JdomTestHelper {
    static final Namespace NS_XSD = Namespace.getNamespace("xs", "http://www.w3.org/2001/XMLSchema");

    static Document createXsdDoc() {
        Document doc = new Document();
        Element root = new Element("schema", NS_XSD);
        root.setAttribute("targetNamespace", "http://cloudme.org/metamodel");
        root.setAttribute("elementFormDefault", "qualified");
//        root.setAttribute("attributeFormDefault", "qualified");
        root.addContent(new Element("element", NS_XSD).setAttribute("name", "system").addContent(new Element("complexType", NS_XSD).addContent(new Element("sequence", NS_XSD).addContent(Arrays.asList(new Element("element", NS_XSD).setAttribute("name", "name").setAttribute("type", "xs:string"), new Element("element", NS_XSD).setAttribute("name", "vendor").setAttribute("type", "xs:string"), new Element("element", NS_XSD).setAttribute("name", "version").setAttribute("type", "xs:decimal"))))));
        doc.setRootElement(root);
        return doc;
    }
}
