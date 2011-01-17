package org.cloudme.mediacopy;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class OtrCopyTest {
	@Test
	public void testCreateFileName() {
		OtrFile otrFile = new OtrFile(new File("Das_Bourne_Ultimatum_10.12.27_22-15_zdf_105_TVOON_DE.mpg.avi"));
		OtrCopy otrCopy = new OtrCopy();
		assertEquals("Das Bourne Ultimatum (ZDF, 2010-12-27 22-15, CUT).mpg.avi", otrCopy.createFileName(otrFile, true));
		assertEquals("Das Bourne Ultimatum (ZDF, 2010-12-27 22-15).mpg.avi", otrCopy.createFileName(otrFile, false));
	}
}
