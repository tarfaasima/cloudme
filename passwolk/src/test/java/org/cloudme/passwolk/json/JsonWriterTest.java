package org.cloudme.passwolk.json;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import org.cloudme.passwolk.json.JsonWriter.Order;
import org.junit.Before;
import org.junit.Test;

public class JsonWriterTest {
	public static class Person implements JsonSerializable {
		private String name;
		private long age;
		private Person parent;

		public Person() {
		}

		public Person(long age, String name) {
			this.age = age;
			this.name = name;
		}

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

		@Override
		public String[] serializableProperties() {
			return new String[] { "age", "name", "parent" };
		}
	}

	private ByteArrayOutputStream out;
	private JsonWriter writer;

	@Before
	public void init() {
		out = new ByteArrayOutputStream();
		writer = new JsonWriter(new PrintWriter(out), Order.SORTED);
	}

	@Test
	public void testWithStringArray() {
		String[] names = { "Max", "Jan", "Karl" };
		assertJson("[\"Max\",\"Jan\",\"Karl\"]", names);
	}

	@Test
	public void testWithJsonSerializable() {
		Person person1 = new Person(42, "Joe Max");
		List<Person> persons = Arrays.asList(person1);

		assertJson("[{\"age\":42,\"name\":\"Joe Max\",\"parent\":null}]", persons);

		Person person2 = new Person(66, "John Smith");
		person1.setParent(person2);

		init();

		assertJson(
				"[{\"age\":42,\"name\":\"Joe Max\",\"parent\":{\"age\":66,\"name\":\"John Smith\",\"parent\":null}}]",
				persons);
	}

	private void assertJson(String expected, Object obj) {
		writer.write(obj);
		writer.flush();
		assertEquals(expected, new String(out.toByteArray()));
	}
}
