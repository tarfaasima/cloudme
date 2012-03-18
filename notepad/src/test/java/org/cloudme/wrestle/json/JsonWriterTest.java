package org.cloudme.wrestle.json;

import java.io.PrintWriter;
import java.io.StringWriter;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.junit.Test;

public class JsonWriterTest {
    @Data
    @AllArgsConstructor
    public static class Person {
        private String name;
        private long age;
    }

    @Test
    public void testWrite() {
        StringWriter stringWriter = new StringWriter();
        JsonWriter json = new JsonWriter(new PrintWriter(stringWriter));
        json.write(new Person("Max Mustermann", 35));
    }

}
