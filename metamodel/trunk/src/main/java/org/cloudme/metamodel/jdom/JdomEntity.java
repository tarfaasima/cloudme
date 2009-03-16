package org.cloudme.metamodel.jdom;

import org.cloudme.metamodel.Entity;
import org.cloudme.metamodel.Instance;
import org.cloudme.metamodel.Type;
import org.jdom.Element;
import org.jdom.Namespace;

class JdomEntity implements Entity {
    private final Element element;

    JdomEntity(Element element) {
        if (element == null) {
            throw new NullPointerException("Element must not be null");
        }
        this.element = element;
    }

    public String getName() {
        return element.getAttributeValue("name");
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
    public String toString() {
        return getName();
    }
    
    @Override
    public int hashCode() {
        return element.hashCode();
    }

    public void addProperty(String name, Type type) {
        Element sequence = getChild("complexType", "sequence");
        sequence.setAttribute("name", name);
        sequence.setAttribute("type", type.getXsdDataType());
    }

    private Element getChild(String... names) {
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

    public Instance newInstance() {
        return new JdomInstance(this);
    }
}
