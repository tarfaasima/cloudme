package org.cloudme.wrestle.annotation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UrlMappingTest {

    @UrlMapping( "A" )
    public static class A {
    }

    @UrlMapping( "/B" )
    public static class B {
    }

    @UrlMapping( "C/" )
    public static class C {
    }

    @UrlMapping( "/D/" )
    public static class D {
    }

    public static class E {
    }

    @Test
    public void testNormalizedValue() {
        assertEquals("/A", UrlMapping.Util.normalizedValue(new A()));
        assertEquals("/B", UrlMapping.Util.normalizedValue(new B()));
        assertEquals("/C", UrlMapping.Util.normalizedValue(new C()));
        assertEquals("/D", UrlMapping.Util.normalizedValue(new D()));
        assertEquals("", UrlMapping.Util.normalizedValue(new E()));
    }

}
