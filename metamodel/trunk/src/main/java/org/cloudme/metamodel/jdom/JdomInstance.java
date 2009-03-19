package org.cloudme.metamodel.jdom;

import org.cloudme.metamodel.Instance;
import org.cloudme.metamodel.MetamodelException;
import org.jdom.Element;

public class JdomInstance extends AbstractJdomObject implements Instance {
    private final JdomEntity entity;

    public JdomInstance(JdomEntity entity) {
        super(new Element(entity.getName()));
        this.entity = entity;
    }

    public void setValue(String name, String value) {
        if (!entity.hasProperty(name)) {
            throw new MetamodelException("Unknown property: " + name);
        }
        getChild(name).setText(value);
    }

    public boolean validate() {
        throw new UnsupportedOperationException();
    }
}
