package org.cloudme.metamodel.jdom;

import java.util.Collection;
import java.util.List;

import org.cloudme.metamodel.Entity;
import org.cloudme.metamodel.Instance;
import org.cloudme.metamodel.Property;
import org.cloudme.metamodel.Type;
import org.cloudme.metamodel.util.ConvertCollection;
import org.jdom.Element;
import org.jdom.Namespace;

class JdomEntity extends AbstractJdomObject implements Entity {
    static final Namespace NS_EXT = Namespace.getNamespace("ext", "http://cloudme.org/metamodel/ext");

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
    
    public void addProperty(String name, Type type, String label) {
        Element propertyElement = getPropertyElement(name);
        if (propertyElement == null) {
            Element sequence = getChild("complexType", "sequence");
            propertyElement = new Element("element", sequence.getNamespace());
            sequence.addContent(propertyElement);
            propertyElement.setAttribute("name", name);
        }
        propertyElement.setAttribute("type", type.getXsdDataType());
        propertyElement.setAttribute("label", label, NS_EXT);
    }
    
    @SuppressWarnings("unchecked")
    public Collection<Property> getProperties() {
        String expr = "descendant::xs:element";
        Namespace namespace = element.getNamespace();
        XPathHandler xpath = new XPathHandler(expr, namespace);
        List nodes = xpath.selectNodes(element);
        return new ConvertCollection<Element, Property>(nodes) {
            @Override
            protected Property convert(Element e) {
                return new JdomProperty(e);
            }
        };
    }

    public Instance newInstance() {
        return new JdomInstance(this);
    }

    public boolean hasProperty(String name) {
        return getPropertyElement(name) != null;
    }
    
    private Element getPropertyElement(String name) {
        String expr = "descendant::xs:element[@name='" + name + "']";
        Namespace namespace = element.getNamespace();
        XPathHandler xpath = new XPathHandler(expr, namespace);
        Element node = (Element) xpath.selectSingleNode(element);
        return node;
    }
}
