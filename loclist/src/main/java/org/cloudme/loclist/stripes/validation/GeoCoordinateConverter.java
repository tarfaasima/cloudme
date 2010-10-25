package org.cloudme.loclist.stripes.validation;

import java.util.Collection;
import java.util.Locale;

import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;

public class GeoCoordinateConverter implements TypeConverter<Float> {
    @Override
    public Float convert(String input, Class<? extends Float> targetType, Collection<ValidationError> errors) {
        return Float.valueOf(input);
    }

    @Override
    public void setLocale(Locale locale) {
    }
}
