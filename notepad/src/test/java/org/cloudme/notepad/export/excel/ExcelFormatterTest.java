package org.cloudme.notepad.export.excel;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExcelFormatterTest {

    @Test
    public void testFormatString() {
        assertFormat("A\n\u2022 B\n\u2022 C", "A\n- B\n- C");
    }

    private void assertFormat(String expected, String input) {
        assertEquals(expected, new ExcelFormatter().format(input));
    }

}
