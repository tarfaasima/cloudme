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
				bind(CopyListener.class).toInstance(new CopyListener() {
					private long initialized;

					@Override
					public void copySuccess(File src, File dest) {
						System.out.println("Successful");
					}
					
					@Override
					public void copyStarted(File src, File dest) {
						System.out.printf("Copying %s --> %s%n", src.getName(), dest.getName());
					}
					
					@Override
					public void copyPrepared(int count, long size) {
						System.out.printf("Copying %d files (%d byte)%n", count, size);
					}
					
					@Override
					public void copyInitialized() {
						initialized = System.nanoTime();
					}
					
					@Override
					public void copyFailed(File src, File dest, String message) {
						System.out.printf("Failed (%s)%n", message);
					}
					
					@Override
					public void copyCompleted() {
						System.out.println("Copy completed in " + (System.nanoTime() - initialized) + "ns");
					}

					@Override
					public void copySkipped(File src, File dest) {
						System.out.println("Skipped");
					}
				});
			}
		});
		// Test setup
        File tmp = new File("target/tmp");
        File cutDir = new File(tmp, "cutDir");
        File destDir = new File(tmp, "destDir");
		FileLog fileLog = new FileLog(new File(tmp, "otrCopy.log"));
        File originalsDir = new File(tmp, "originalsDir");
        FileUtils.deleteQuietly(tmp);
        File b = new File(originalsDir, "Das_Bourne_Ultimatum_10.12.27_22-15_zdf_105_TVOON_DE.mpg.avi");
		FileUtils.writeByteArrayToFile(b, new byte[10000]);
        OtrCopy otrCopy = new OtrCopy();
		injector.injectMembers(otrCopy);
        otrCopy.setCutDir(cutDir.getAbsolutePath());
        otrCopy.setDestDir(destDir.getAbsolutePath());
        otrCopy.setOriginalsDir(originalsDir.getAbsolutePath());
		otrCopy.setFileLog(fileLog);
        otrCopy.copy();
		assertEquals(1, destDir.list().length);
        File[] list = new File(destDir, "D").listFiles();
        assertEquals(1, list.length);
        assertEquals("Das Bourne Ultimatum (ZDF, 2010-12-27 22-15).mpg.avi", list[0].getName());
        long lastModified = list[0].lastModified();
        Thread.sleep(1000);
        otrCopy.copy();
        list = new File(destDir, "D").listFiles();
        assertEquals(1, list.length);
        assertTrue(lastModified + " != " + list[0].lastModified(), lastModified == list[0].lastModified());
    }
}
