package org.cloudme.metamodel;


public class MetamodelException extends RuntimeException {
    public MetamodelException(String msg) {
        super(msg);
    }

    public MetamodelException(Throwable t) {
        super(t);
    }
}
