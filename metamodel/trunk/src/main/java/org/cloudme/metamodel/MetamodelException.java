package org.cloudme.metamodel;

/**
 * The basic exception that is thrown on error situations.
 * 
 * @author <a href="mailto:moritz@cloudme.org">Moritz Petersen</a>
 */
@SuppressWarnings( "serial" )
public class MetamodelException extends RuntimeException {
    /**
     * Creates a new {@link MetamodelException} with the given message.
     * 
     * @param msg
     *            The message of this exception.
     */
    public MetamodelException(String msg) {
        super(msg);
    }

    /**
     * Creates a new {@link MetamodelException} with the given caused
     * {@link Throwable}.
     * 
     * @param t The {@link Throwable} that has caused this exception.
     */
    public MetamodelException(Throwable t) {
        super(t);
    }
}
