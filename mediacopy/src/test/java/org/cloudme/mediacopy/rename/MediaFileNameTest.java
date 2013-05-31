package org.cloudme.mediacopy.rename;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class MediaFileNameTest {

    @Test
    public void testCreate() {
        assertNull(MediaFileName.create("hello.txt"));
        assertEquals("Come Dine with Me (2013-05-27 12-05, UKCHANNEL4).mpg.avi",
                     MediaFileName.create("Come Dine with Me (UKCHANNEL4, 2013-05-27 12-05).mpg.avi").toNewName());
        assertEquals("Die Bourne Verschwoerung (2010-12-26 22-15, ZDF, CUT).mpg.avi",
                     MediaFileName.create("Die Bourne Verschwoerung (ZDF, 2010-12-26 22-15, CUT).mpg.avi").toNewName());
    }

}
