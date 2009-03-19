package org.cloudme.metamodel.jdom;

import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


abstract class AbstractJdomObject {
    protected final Element element;

    protected AbstractJdomObject(Element element) {
        if (element == null) {
            throw new NullPointerException("Element must not be null");
        }
        this.element = element;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof JdomEntity) {
            return element.equals(((JdomEntity) obj).element);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return element.hashCode();
    }

    protected Element getChild(String... names) {
        Element child = element;
        for (String name : names) {
            child = getChild(child, name);
        }
        return child;
    }

    private Element getChild(Element parent, String name) {
        Namespace namespace = parent.getNamespace();
        Element child = parent.getChild(name, namespace);
        if (child == null) {
            child = new Element(name, namespace);
            parent.addContent(child);
        }
        return child;
    }
    
    @Override
    public String toString() {
        return new XMLOutputter(Format.getPrettyFormat()).outputString(element);
    }
}
