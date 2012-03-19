package org.cloudme.wrestle.json;

import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.junit.Test;

public class JsonWriterTest {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Data
    @AllArgsConstructor
    public static class Person {
        private String name;
        private long age;
    }

    @Test
    public void testWrite() throws Throwable {
        assertWrite("null", null);
        assertWrite("[\"A\",\"B\",\"C\"]", new String[] { "A", "B", "C" });
        assertWrite("false", false);
        assertWrite("3.1415", 3.1415);
        assertWrite("\"a\\nb\\tc\"", "a\nb\tc");
        assertWrite("\"\\/Date(1332543600000+0100)\\/\"", DATE_FORMAT.parse("2012-03-24"));
        assertWrite("{\"name\":\"Max Mustermann\",\"age\":35}", new Person("Max Mustermann", 35));
    }

    private void assertWrite(String expected, Object input) {
        StringWriter stringWriter = new StringWriter();
        JsonWriter json = new JsonWriter(new PrintWriter(stringWriter));
        json.write(input);
        stringWriter.flush();
        assertEquals(expected, stringWriter.toString());
    }
}
