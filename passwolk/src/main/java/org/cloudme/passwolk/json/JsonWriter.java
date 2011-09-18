package org.cloudme.passwolk.json;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.cloudme.passwolk.util.StringUtils;

/**
 * Provides basic support to write JSON data from Java objects.
 * <p>
 * If a {@link JsonSerializable} object is used, the writer will automatically
 * serialize all provided properties.
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
	 * Calls {@link PrintWriter#flush()} of the internal {@link PrintWriter}.
	 */
	public void flush() {
		out.flush();
	}

	/**
	 * Writes the properties of the given {@link JsonSerializable} object in JSON notation.
	 * 
	 * @param jsonSerializable
	 *            The {@link JsonSerializable} object.
	 */
	public void write(JsonSerializable jsonSerializable) {
		write(describe(jsonSerializable));
	}

	/**
	 * Writes a {@link Map} in JSON notation.
	 * 
	 * @param map
	 *            the {@link Map} that is serialized.
	 */
	public void write(Map<?, ?> map) {
		out.write('{');
		boolean isFirst = true;
		for (Entry<?, ?> property : map.entrySet()) {
			if (isFirst) {
				isFirst = false;
			} else {
				out.write(',');
			}
			write(String.valueOf(property.getKey()));
			out.write(':');
			write(property.getValue());
		}
		out.write('}');
	}

	/**
	 * Writes a {@link String} in JSON notation.
	 * 
	 * @param str
	 *            the {@link String} that will be written.
	 */
	public void write(String str) {
		out.write('"');
		out.write(str);
		out.write('"');
	}

	/**
	 * Writes the {@link Iterable} in JSON array notation.
	 * 
	 * @param iterable
	 *            The {@link Iterable}.
	 */
	public void write(final Iterable<?> iterable) {
		out.write('[');
		boolean isFirst = true;
		for (Object object : iterable) {
			if (isFirst) {
				isFirst = false;
			} else {
				out.write(',');
			}
			write(object);
		}
		out.write(']');
	}

	/**
	 * Writes the given array in JSON notation.
	 * 
	 * @param array
	 *            the array.
	 */
	public void write(final Object[] array) {
		write(Arrays.asList(array));
	}

	/**
	 * Writes a single {@link Object}.
	 * 
	 * @param obj
	 *            The object that ist transformed to JSON.
	 * @see JsonSerializable
	 */
	public void write(Object obj) {
		if (obj == null) {
			out.write("null");
		} else if (obj instanceof JsonSerializable) {
			write((JsonSerializable) obj);
		} else if (obj instanceof String) {
			write((String) obj);
		} else if (obj instanceof Iterable<?>) {
			write((Iterable<?>) obj);
		} else if (obj.getClass().isArray()) {
			write((Object[]) obj);
		} else {
			out.write(String.valueOf(obj));
		}
	}

	/**
	 * Creates a map containing all property names and values of the object.
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
            if (!StringUtils.isEmpty(propertyName)) {
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
