package org.cloudme.mediacopy;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Test;

public class OtrFileTest {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    @Test
    public void testAttributes() {
        String fileName = "Das_Bourne_Ultimatum_10.12.27_22-15_zdf_105_TVOON_DE.mpg.avi";
        OtrFile otrFile = new OtrFile(new File(fileName));
        assertEquals("mpg.avi", otrFile.getSuffix());
        assertEquals("ZDF", otrFile.getChannel());
        assertEquals("27.12.2010 22:15", DATE_FORMAT.format(otrFile.getDate()));
        assertEquals("Das Bourne Ultimatum", otrFile.getTitle());
    }

	@Test(expected = IllegalArgumentException.class)
    public void testWithoutSuffix() {
        String fileName = "Das_Bourne_Ultimatum_10.12.27_22-15_zdf_105_TVOON_DE";
        new OtrFile(new File(fileName));
    }

	@Test(expected = IllegalArgumentException.class)
    public void testIncompleteFileName() {
        String fileName = "10.12.27_22-15_zdf_105_TVOON_DE.mpg.avi";
        new OtrFile(new File(fileName));
    }
}
