package de.moritzpetersen.homepage.mail;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MailServiceProviderTest {

    @Test
    public void testExtractToken() {
        MailServiceProvider provider = new MailServiceProvider();
        assertEquals("123", provider.extractToken("123@some.com"));
    }

}
