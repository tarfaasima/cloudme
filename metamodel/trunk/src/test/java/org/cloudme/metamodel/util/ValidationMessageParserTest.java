package org.cloudme.metamodel.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;


public class ValidationMessageParserTest {
    @Test
    public void testMessageParser() {
        ValidationMessageParser parser = new ValidationMessageParser();
        ValidationError error = parser.parse("cvc-type.3.1.3: The value '1a' of element 'version' is not valid.");
        assertEquals("1a", error.getValue());
        assertEquals("version", error.getProperty());
        assertNull(parser.parse("somthing, but not an error message"));
    }
}
