package org.cloudme.triangle.format;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Formats {@link Number}s.
 * 
 * @see DecimalFormat
 * @author Moritz Petersen
 */
public class NumberFormatter implements Formatter<Number> {
    /**
     * The format instance used to format the value.
     */
    private NumberFormat format;

    /**
     * Sets the format pattern. If the pattern is null, a default
     * {@link NumberFormat} is used, otherwise a {@link DecimalFormat} is
     * created.
     */
    @Override
    public void setPattern(String pattern) {
        if (pattern == null) {
            format = NumberFormat.getInstance();
        }
        else {
            format = new DecimalFormat(pattern);
        }
    }

    /**
     * Formats a raw value to a printable {@link String}.
     * 
     * @param value
     *            The raw value.
     * @return The formatted {@link String}.
     */
    @Override
    public String format(Number value) {
        return format.format(value);
    }
}
