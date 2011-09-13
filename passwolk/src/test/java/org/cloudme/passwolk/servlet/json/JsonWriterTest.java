package org.cloudme.passwolk.servlet.json;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class JsonWriterTest {

    public static class Person {
        private String name;
        private long age;
        private Person parent;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getAge() {
            return age;
        }

        public void setAge(long age) {
            this.age = age;
        }

        public Person getParent() {
            return parent;
        }

        public void setParent(Person parent) {
            this.parent = parent;
        }
    }

    @Test
    public void testJson() {
        Person person1 = new Person();
        person1.setAge(42);
        person1.setName("Joe Max");

        List<Person> persons = Arrays.asList(person1);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JsonWriter writer = new JsonWriter(new PrintWriter(out));
        writer.write(persons);

        assertEquals("[{\"name\":\"Joe Max\",\"age\":42,\"parent\":null}]", new String(out.toByteArray()));

        Person person2 = new Person();
        person2.setAge(66);
        person2.setName("John Smith");
        person1.setParent(person2);

        out = new ByteArrayOutputStream();
        writer = new JsonWriter(new PrintWriter(out));
        writer.write(persons);

        assertEquals("[{\"name\":\"Joe Max\",\"age\":42,\"parent\":{\"name\":\"John Smith\",\"age\":66,\"parent\":null}}]",
                new String(out.toByteArray()));
    }

}
