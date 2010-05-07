package org.cloudme.triangle.format;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NumberFormatterTest {
    @Test
    public void testFormat() {
        final NumberFormatter formatter = new NumberFormatter();
        formatter.setPattern("00.0");
        assertEquals("82.0", formatter.format(82));
    }
}
