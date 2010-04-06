package org.cloudme.webgallery.stripes.extensions;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Locale;

import net.sourceforge.stripes.validation.ValidationError;

import org.junit.Test;


public class PercentTypeConverterTest {
    @Test
    public void testConvert() {
        assertConvert(0.5f, "50%");
    }

    private void assertConvert(float expected, String input) {
        PercentTypeConverter converter = new PercentTypeConverter();
        converter.setLocale(Locale.getDefault());
        assertEquals(expected, converter.convert(input, Float.class, new ArrayList<ValidationError>()), 0.0001f);
    }
}
