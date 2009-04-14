package org.cloudme.metamodel;

import org.cloudme.metamodel.jdom.JdomMetamodelFactory;

/**
 * The factory to create {@link Metamodel}s.
 * 
 * @author <a href="mailto:moritz@cloudme.org">Moritz Petersen</a>
 */
public abstract class MetamodelFactory {
    /**
     * Creates a new {@link Metamodel}.
     *  
     * @return A new {@link Metamodel}.
     */
    public abstract Metamodel createMetamodel();

    /**
     * Creates a new factory.
     * 
     * @return A new factory.
     */
    public static MetamodelFactory newInstance() {
        return new JdomMetamodelFactory();
    }
}
