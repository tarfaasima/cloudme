package org.cloudme.metamodel;

import org.cloudme.metamodel.jdom.JdomMetamodelFactory;

public abstract class MetamodelFactory {
    public abstract Metamodel newMetamodel();
    
    public static MetamodelFactory newInstance() {
        return new JdomMetamodelFactory();
    }
}
