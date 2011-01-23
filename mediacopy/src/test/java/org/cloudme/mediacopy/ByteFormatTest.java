package org.cloudme.mediacopy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ByteFormatTest {
    @Test
    public void testFormat() {
        assertEquals("1MB", ByteFormat.formatMB(1024 * 1024 + 200));
        assertEquals("200MB", ByteFormat.formatMB(1024 * 1024 * 200));
    }
}
