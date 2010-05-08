package org.cloudme.triangle.format;

import java.text.Format;

/**
 * Abstract base class to simplify implementation of {@link Formatter}s.
 * 
 * @author Moritz Petersen
 * 
 * @param <T>
 *            Type of the input value.
 */
public abstract class AbstractFormatter<T> implements Formatter<T> {
    /**
     * {@link Format} used to format raw value to {@link String}.
     */
    private Format format;

    @Override
    public String format(T value) {
        return format.format(value);
    }

    @Override
    public void setPattern(String pattern) {
        format = createFormat(pattern);
    }

    /**
     * Creates the {@link Format} instance for this {@link Formatter}.
     * 
     * @param pattern
     *            The pattern which is used to initialize the {@link Format};
     * @return The {@link Formatter} instance.
     */
    protected abstract Format createFormat(String pattern);
}
