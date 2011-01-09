package org.cloudme.cashbox.csv;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CsvParserTest {
    @Test
    public void testParse() {
        CsvParser csv = new CsvParser();
        String line = "\"13.12.2010 \";\"13.12.2010 \";\"Lastschrift Einzug\";\"Auftraggeber: IKEA HAMBURG Buchungstext: 11.12 16.58 Ref. xxxxxxxx/xxxx \";\"-12,34\"";
        String[] items = csv.parse(line);
        assertEquals(5, items.length);
        assertEquals("13.12.2010", items[0]);
    }

    @Test
    public void testParseWithoutBoundary() {
        CsvParser csv = new CsvParser();
        String[] items = csv.parse("\"test;ABC ;2");
        assertEquals("ABC", items[1]);
    }

    @Test
    public void testIsEmpty() {
        CsvParser csv = new CsvParser();
        assertEquals(0, csv.parse("").length);
    }
}
