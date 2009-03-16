package org.cloudme.metamodel.jdom;

import java.util.List;

import org.cloudme.metamodel.MetamodelException;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;

class XPathHandler {
    private XPath xpath;

    public XPathHandler(String xpathExpression, Namespace namespace) {
        try {
            xpath = XPath.newInstance(xpathExpression);
        }
        catch (JDOMException e) {
            throw new MetamodelException("Invalid XPath expression: " + xpathExpression);
        }
        xpath.addNamespace(namespace);
    }

    @SuppressWarnings("unchecked")
    public List selectNodes(Document doc) {
        try {
            return xpath.selectNodes(doc);
        }
        catch (JDOMException e) {
            throw new MetamodelException("XPath expression evaluation failed: " + xpath.getXPath());
        }
    }
}
