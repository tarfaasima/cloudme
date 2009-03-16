package org.cloudme.metamodel.jdom;

import java.util.Collection;
import java.util.List;

import org.cloudme.metamodel.Entity;
import org.cloudme.metamodel.Metamodel;
import org.cloudme.metamodel.util.ConvertCollection;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

class JdomMetamodel implements Metamodel {
    private static final Namespace NS_XSD = Namespace.getNamespace("xs", "http://www.w3.org/2001/XMLSchema");
    private static final XPathHandler XPATH_GET_ENTITIES = new XPathHandler("/xs:schema/xs:element", NS_XSD);
    private Document xsd = new Document(new Element("schema", NS_XSD));

    @SuppressWarnings("unchecked")
    public Collection<Entity> getEntities() {
        List nodes = XPATH_GET_ENTITIES.selectNodes(xsd);
        return new ConvertCollection<Element, Entity>(nodes) {
            @Override
            protected Entity convert(Element element) {
                return new JdomEntity(element);
            }
        };
    }

    public Entity newEntity(String name) {
        Element element = new Element("element", NS_XSD);
        element.setAttribute("name", name);
        xsd.getRootElement().addContent(element);
        return new JdomEntity(element);
    }
}
