package org.cloudme.triangle.convert;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Converts {@link Number}s.
 * 
 * @see DecimalFormat
 * @author Moritz Petersen
 */
public class NumberConverter implements Converter<Number> {
    /**
     * The format instance used to parse the value.
     */
    private NumberFormat format;

    /**
     * Sets the format pattern.
     */
    @Override
    public void setPattern(String pattern) {
        format = new DecimalFormat(pattern);
    }

    /**
     * Converts the given {@link String} into a {@link Number}.
     */
    @Override
    public Number convert(String str) {
        try {
            return format.parse(str);
        }
        catch (final ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
