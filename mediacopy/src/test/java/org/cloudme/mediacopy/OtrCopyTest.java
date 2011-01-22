package org.cloudme.mediacopy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class OtrCopyTest {
    @Test
    public void testCopy() throws IOException, InterruptedException {
    	// Guice setup
    	Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
                bind(CopyListener.class).toInstance(new SystemListener());
			}
		});
		// Test setup
        File tmp = new File("target/tmp");
        File cutDir = new File(tmp, "cutDir");
        File destDir = new File(tmp, "destDir");
		FileLog fileLog = new FileLog(new File(tmp, "otrCopy.log"));
        File originalsDir = new File(tmp, "originalsDir");
        FileUtils.deleteQuietly(tmp);
        createFile(originalsDir, "Das_Bourne_Ultimatum_10.12.27_22-15_zdf_105_TVOON_DE.mpg.avi");
        createFile(originalsDir, "Die_Bourne_Verschwoerung_10.12.26_22-15_zdf_105_TVOON_DE.mpg.avi");
        createFile(cutDir, "Die_Bourne_Verschwoerung_10.12.26_22-15_zdf_105_TVOON_DE.mpg.avi");
        createFile(cutDir, "Die_Bourne_Identitaet_10.12.25_22-15_zdf_105_TVOON_DE.mpg.avi");
        OtrCopy otrCopy = new OtrCopy();
		injector.injectMembers(otrCopy);
        otrCopy.setCutDir(cutDir.getAbsolutePath());
        otrCopy.setDestDir(destDir.getAbsolutePath());
        otrCopy.setOriginalsDir(originalsDir.getAbsolutePath());
		otrCopy.setFileLog(fileLog);
        otrCopy.copy();
		assertEquals(1, destDir.list().length);
        File[] list = new File(destDir, "D").listFiles();
        assertEquals(3, list.length);
        assertEquals("Das Bourne Ultimatum (ZDF, 2010-12-27 22-15).mpg.avi", list[0].getName());
        assertEquals("Die Bourne Identitaet (ZDF, 2010-12-25 22-15, CUT).mpg.avi", list[1].getName());
        assertEquals("Die Bourne Verschwoerung (ZDF, 2010-12-26 22-15, CUT).mpg.avi", list[2].getName());
        long lastModified = list[0].lastModified();
        Thread.sleep(1000);
        otrCopy.copy();
        list = new File(destDir, "D").listFiles();
        assertEquals(3, list.length);
        assertTrue(lastModified + " != " + list[0].lastModified(), lastModified == list[0].lastModified());
    }

    private void createFile(File originalsDir, String title) throws IOException {
        File b = new File(originalsDir, title);
        FileUtils.writeByteArrayToFile(b, new byte[1000000]);
    }
}
