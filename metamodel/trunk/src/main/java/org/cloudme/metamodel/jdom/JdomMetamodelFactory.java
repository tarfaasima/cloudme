package org.cloudme.metamodel.jdom;

import org.cloudme.metamodel.Metamodel;
import org.cloudme.metamodel.MetamodelFactory;

public class JdomMetamodelFactory extends MetamodelFactory {
    @Override
    public Metamodel createMetamodel() {
        return new JdomMetamodel();
    }
}
