package org.cloudme.triangle.format;

/**
 * Formats a raw value to a printable {@link String}.
 * 
 * @see FormatterFactory
 * @author Moritz Petersen
 * @param <T>
 *            The type of the raw value.
 */
public interface Formatter<T> {
    /**
     * The {@link Formatter} pattern.
     * 
     * @param pattern
     *            The {@link Formatter} pattern.
     */
    void setPattern(String pattern);

    /**
     * Formats a raw value to a printable {@link String}.
     * 
     * @param value
     *            The raw value.
     * @return The formatted {@link String}.
     */
    String format(T value);
}
