package org.cloudme.metamodel.jdom;

import org.cloudme.metamodel.Entity;
import org.cloudme.metamodel.Instance;
import org.cloudme.metamodel.Type;
import org.jdom.Element;
import org.jdom.Namespace;

class JdomEntity extends AbstractJdomObject implements Entity {
    JdomEntity(Element element) {
        super(element);
    }

    public String getName() {
        return element.getAttributeValue("name");
    }

    @Override
    public String toString() {
        return getName();
    }
    
    public void addProperty(String name, Type type) {
        Element propertyElement = getPropertyElement(name);
        if (propertyElement == null) {
            Element sequence = getChild("complexType", "sequence");
            propertyElement = new Element("element", sequence.getNamespace());
            sequence.addContent(propertyElement);
            propertyElement.setAttribute("name", name);
        }
        propertyElement.setAttribute("type", type.getXsdDataType());
    }

    public Instance newInstance() {
        return new JdomInstance(this);
    }

    public boolean hasProperty(String name) {
        return getPropertyElement(name) != null;
    }
    
    private Element getPropertyElement(String name) {
        String expression = "descendant::xs:element[@name='" + name + "']";
        Namespace namespace = element.getNamespace();
        XPathHandler xpath = new XPathHandler(expression, namespace);
        Element node = (Element) xpath.selectSingleNode(element);
        return node;
    }
}
