package org.cloudme.metamodel.jdom;

import org.cloudme.metamodel.Property;
import org.cloudme.metamodel.Type;
import org.jdom.Element;

public class JdomProperty implements Property {
    private final String name;
    private final Type type;
    private final String label;

    public JdomProperty(Element element) {
        name = element.getAttributeValue("name");
        type = Type.get(element.getAttributeValue("type"));
        label = element.getAttributeValue("label", JdomEntity.NS_EXT);
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }
}
