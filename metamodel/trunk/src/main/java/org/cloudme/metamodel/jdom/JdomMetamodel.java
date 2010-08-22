package org.cloudme.metamodel.jdom;

import java.util.Collection;
import java.util.List;

import org.cloudme.metamodel.Entity;
import org.cloudme.metamodel.Metamodel;
import org.cloudme.metamodel.util.ConvertCollection;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

class JdomMetamodel implements Metamodel {
    private static final Namespace NS_XSD = Namespace.getNamespace("xs", "http://www.w3.org/2001/XMLSchema");
    private static final XPathHandler XPATH_GET_ENTITIES = new XPathHandler("/xs:schema/xs:element", NS_XSD);
    private final Document xsd;

    JdomMetamodel() {
        Element root = new Element("schema", NS_XSD);
        root.setAttribute("targetNamespace", "http://cloudme.org/metamodel");
        root.setAttribute("elementFormDefault", "qualified");
        root.addNamespaceDeclaration(JdomEntity.NS_EXT);
        xsd = new Document(root);
    }

    public Collection<Entity> getEntities() {
        List<Element> nodes = XPATH_GET_ENTITIES.selectNodes(xsd);
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

    @Override
    public String toString() {
        return new XMLOutputter(Format.getPrettyFormat()).outputString(xsd);
    }
}
