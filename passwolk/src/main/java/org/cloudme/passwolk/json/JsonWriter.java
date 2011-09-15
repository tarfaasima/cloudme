package org.cloudme.passwolk.json;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * Provides basic support to write JSON data from Java objects. Currently
 * supports {@link Iterable<JsonSerializable>} and {@link JsonSerializable}.
 * <p>
 * This class requires a {@link PrintWriter} to generate output.
 * <p>
 * The {@link JsonWriter} can produce sorted output, which means that the
 * properties will be serialized in alphabetical order. This can be useful for
 * testing.
 * 
 * @author Moritz Petersen
 * @see JsonSerializable
 */
public class JsonWriter {
	/**
	 * Specifies the order of the output.
	 * 
	 * @author Moritz Petersen
	 */
	public enum Order {
		/**
		 * The output will be in alphabetical order. This is useful for testing,
		 * but slower than {@link #REGULAR}.
		 */
		SORTED {
			@Override
			Map<String, Object> createMap() {
				return new TreeMap<String, Object>();
			}
		},
		/**
		 * The output will be in normal order, which means: unpredictable. This
		 * output will be more performant than {@link #SORTED}.
		 */
		REGULAR {
			@Override
			Map<String, Object> createMap() {
				return new HashMap<String, Object>();
			}
		};

		abstract Map<String, Object> createMap();
	}

	/**
	 * The internal writer.
	 */
	private final PrintWriter out;
	/**
	 * The {@link Order} that will be used for output.
	 */
	private final Order order;

	/**
	 * Initializes the {@link JsonWriter} with the given {@link PrintWriter}.
	 * The created instance will create {@link Order#REGULAR} output.
	 * 
	 * @param out
	 *            The {@link PrintWriter} that is used to write JSON data to.
	 */
	public JsonWriter(PrintWriter out) {
		this(out, Order.REGULAR);
	}

	/**
	 * Initializes the {@link JsonWriter} with the given {@link PrintWriter} and
	 * the given {@link Order}.
	 * 
	 * @param out
	 *            The {@link PrintWriter} that is used to write JSON data to.
	 * @param order
	 *            The output {@link Order}.
	 */
	public JsonWriter(PrintWriter out, Order order) {
		this.out = out;
		this.order = order;
	}

	/**
	 * Writes the objects of the given {@link Iterable<JsonSerializable>}.
	 * 
	 * @param iterable
	 *            The input that is transformed to JSON. Each instance must be
	 *            of type {@link JsonSerializable}.
	 */
	public void write(Iterable<? extends JsonSerializable> iterable) {
		out.write("[");
		for (JsonSerializable object : iterable) {
			write(object);
		}
		out.write("]");
		out.flush();
	}

	/**
	 * Writes a single {@link JsonSerializable}.
	 * 
	 * @param obj
	 *            The object that ist transformed to JSON.
	 */
	public void write(JsonSerializable obj) {
		Map<String, Object> props = describe(obj);
		if (props != null) {
			out.write("{");
			boolean isFirst = true;
			for (Entry<String, Object> property : props.entrySet()) {
				String key = property.getKey();
				if (isFirst) {
					isFirst = false;
				} else {
					out.write(',');
				}
				out.write('"');
				out.write(key);
				out.write("\":");
				Object value = property.getValue();
				if (value instanceof JsonSerializable) {
					write((JsonSerializable) value);
				} else if (value instanceof String) {
					out.write('"');
					out.write((String) value);
					out.write('"');
				} else {
					out.write(String.valueOf(value));
				}
			}
			out.write("}");
		}
	}

	/**
	 * Creates a map containing all property names and values of the object.
	 * Please note that the {@link Class} of the {@link Object} must be
	 * initialized first using {@link #init(Class, String...)}.
	 * <p>
	 * In case of any exception while reading properties from the object, the
	 * method will ignore the error and continue gracefully.
	 * 
	 * @param obj
	 *            The object, that must follow the Java Bean syntax (using
	 *            accessors in getXXX() setXXX style()).
	 * 
	 * @return A map containing the properties.
	 */
	private Map<String, Object> describe(JsonSerializable obj) {
		Class<?> clazz = obj.getClass();
		String[] propertyNames = obj.serializableProperties();
		Map<String, Object> properties = order.createMap();
		for (String propertyName : propertyNames) {
			if (!isEmpty(propertyName)) {
				try {
					Method getter = clazz.getMethod(toGetterName(propertyName));
					Object value = getter.invoke(obj);
					properties.put(propertyName, value);
				} catch (SecurityException e) {
					// ignore
				} catch (NoSuchMethodException e) {
					// ignore
				} catch (IllegalArgumentException e) {
					// ignore
				} catch (IllegalAccessException e) {
					// ignore
				} catch (InvocationTargetException e) {
					// ignore
				}
			}
		}
		return properties;
	}

	/**
	 * Convenience method to test if a {@link String} is <code>null</code> or
	 * does not contain any characters. Does not consider trailing spaces.
	 * 
	 * @param str
	 *            The tested {@link String}.
	 * @return <code>true</code> if the {@link String} is <code>null</code> or
	 *         does not contain any characters. Otherwise <code>false</code>.
	 */
	private boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * Creates a getter method name from the property name.
	 * 
	 * @param name
	 *            The name of the property.
	 * @return The name of the getter starting with "get" and followed by the
	 *         name of the property starting with a capital letter.
	 */
	private String toGetterName(String name) {
		int length = name.length();
		StringBuilder getterName = new StringBuilder(length + 3);
		getterName.append("get");
		getterName.append(Character.toUpperCase(name.charAt(0)));
		if (length > 1) {
			getterName.append(name.substring(1));
		}
		return getterName.toString();
	}
}
