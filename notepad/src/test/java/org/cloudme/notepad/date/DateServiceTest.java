package org.cloudme.notepad.date;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DateServiceTest {
    private static final TimeZone DEFAULT_TZ = TimeZone.getDefault();

    @Before
    public void setUtc() {
        System.out.println("Default: " + DEFAULT_TZ);
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @After
    public void restoreDefaultTimeZone() {
        TimeZone.setDefault(DEFAULT_TZ);
    }

    @Test
    public void testConvert() throws Throwable {
        assertConvert("31.01.2012", "31.01.2012");
        assertConvert("31.01.2012", "31.1.2012");
        assertConvert("31.01.2012", "2012-01-31");
        assertConvert("31.01.2012", "2012-1-31");
        assertConvert("01.01.2012", "2012-1-1");
        assertConvert("01.01.2012", "1.1.12");
        assertConvert("31.01.2012", "01/31/2012");
        assertConvert("31.01.2012", "01/31/12");
        assertConvert("31.01.2012", "31012012");
        assertConvert("31.01.2012", "31");
        assertConvert("01.02.2012", "1");
        assertConvert("21.09.2012", "21.9");
        assertConvert("21.09.2012", "21.9.");
        assertConvert("21.09.2012", "21+9");
        assertConvert("21.09.2012", "21 9");
        assertConvert("19.01.2012", "1w");
        assertConvert("19.01.2012", "1W");
        assertConvert("19.01.2012", "1 Week");
        assertConvert("13.01.2012", "1d");
        assertConvert("13.01.2012", "1 day");
        assertConvert("16.01.2012", "2 days");
        assertConvert("26.01.2012", "10 Tage");
        assertConvert("17.01.2013", "53w");
		assertConvert("16.01.2012", "Mo");
		assertConvert("19.01.2012", "Thu");
		assertConvert("13.01.2012", "Fr");
        assertConvert(null, "");
        assertConvert(null, null);
    }

    private void assertConvert(String expected, String input) throws ParseException {
        DateService dateService = new DateService();
        assertEquals(date(expected), dateService.convert(input, date("12.01.2012")));
    }

    private Date date(String str) throws ParseException {
        if (str == null) {
            return null;
        }
        return new SimpleDateFormat("dd.MM.yyyy").parse(str);
    }

}
