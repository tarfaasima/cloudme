package org.cloudme.webgallery.stripes.extensions;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;

public class PercentTypeConverter implements TypeConverter<Float> {
    private Locale locale;

    public Float convert(String input, Class<? extends Float> targetType, Collection<ValidationError> errors) {
        NumberFormat format = DecimalFormat.getPercentInstance(locale);
        try {
            return format.parse(input).floatValue();
        }
        catch (ParseException e) {
            return Float.valueOf(input);
        }
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
