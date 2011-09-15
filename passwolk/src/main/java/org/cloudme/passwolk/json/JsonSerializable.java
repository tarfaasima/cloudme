package org.cloudme.passwolk.json;

/**
 * This interface must be implemented by objects that need to be serialized
 * using the {@link JsonWriter}. It is used to provide all property names that
 * will be considered during serialization.
 * 
 * @author Moritz Petersen
 */
public interface JsonSerializable {
	/**
	 * Provide the properties that will be generated using serialization.
	 * 
	 * @return The property names that will be considered during serialization.
	 */
	String[] serializableProperties();
}
