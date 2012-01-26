package org.cloudme.notepad.jsp.tags;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.TreeSet;

import lombok.val;

import org.junit.Test;

public class TagsTest {

	@Test
	public void testJoin() {
		val input = new TreeSet<String>(Arrays.asList("A", "B", "C"));
		val output = Tags.join(input, "'", "'", ",");
		assertEquals("'A','B','C'", output);
	}

	@Test
	public void testEscapeHtml() {
		assertEquals("Das ist ein<br>T&auml;st!", Tags.escapeHtml("Das ist ein\nTäst!"));
	}

}
