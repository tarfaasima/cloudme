package org.cloudme.cashbox.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CsvStringReaderTest {
    @Test
    public void testParse() {
        CsvString csv = new CsvString();
        String line = "\"13.12.2010 \";\"13.12.2010 \";\"Lastschrift Einzug\";\"Auftraggeber: IKEA HAMBURG Buchungstext: 11.12 16.58 Ref. xxxxxxxx/xxxx \";\"-12,34\"";
        csv.parse(line);
        assertEquals(5, csv.size());
        assertEquals("13.12.2010", csv.get(0));
    }

    @Test
    public void testParseWithoutBoundary() {
        CsvString csv = new CsvString();
        csv.parse("\"test;ABC ;2");
        assertEquals("ABC", csv.get(1));
    }

    @Test
    public void testIsEmpty() {
        CsvString csv = new CsvString();
        assertTrue(csv.isEmpty());
        csv.parse("");
        assertTrue(csv.isEmpty());
    }
}
