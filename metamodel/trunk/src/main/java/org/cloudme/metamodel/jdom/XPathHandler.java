package org.cloudme.metamodel.jdom;

import java.util.List;

import org.cloudme.metamodel.MetamodelException;
import org.jdom.Content;
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
    public List selectNodes(Content content) {
        try {
            return xpath.selectNodes(content);
        }
        catch (JDOMException e) {
            throw new MetamodelException("XPath expression evaluation failed: " + xpath.getXPath());
        }
    }

    @SuppressWarnings("unchecked")
    public List selectNodes(Document doc) {
        return selectNodes(doc.getRootElement());
    }

    public Object selectSingleNode(Content content) {
        try {
            return xpath.selectSingleNode(content);
        }
        catch (JDOMException e) {
            throw new MetamodelException("XPath expression evaluation failed: " + xpath.getXPath());
        }
    }
}
