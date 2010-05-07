package org.cloudme.triangle.convert;


/**
 * Converts a {@link String} to a raw value.
 * 
 * @see ConverterFactory
 * @author Moritz Petersen
 * @param <T>
 *            The type of the raw value.
 */
public interface Converter<T> {
    /**
     * The pattern that is used to parse the {@link String}.
     * 
     * @param pattern
     *            The pattern that is used to parse the {@link String}.
     */
    void setPattern(String pattern);

    /**
     * Converts a {@link String} to a raw value.
     * 
     * @param str
     *            The formatted string, that is parsed.
     * @return The raw value.
     */
    T convert(String str);
}
