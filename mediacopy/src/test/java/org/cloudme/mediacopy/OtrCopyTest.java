package org.cloudme.mediacopy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class OtrCopyTest {
    @Test
    public void testCreateFileName() {
        OtrFile otrFile = new OtrFile(new File("Das_Bourne_Ultimatum_10.12.27_22-15_zdf_105_TVOON_DE.mpg.avi"));
        OtrCopy otrCopy = new OtrCopy();
        assertEquals("Das Bourne Ultimatum (ZDF, 2010-12-27 22-15, CUT).mpg.avi", otrCopy.createFileName(otrFile, true));
        assertEquals("Das Bourne Ultimatum (ZDF, 2010-12-27 22-15).mpg.avi", otrCopy.createFileName(otrFile, false));
    }

    @Test
    public void testCopy() throws IOException, InterruptedException {
        File tmp = new File("target/tmp");
        File cutDir = new File(tmp, "cutDir");
        File destDir = new File(tmp, "destDir");
        File originalsDir = new File(tmp, "originalsDir");
        FileUtils.deleteQuietly(tmp);
        File b = new File(originalsDir, "Das_Bourne_Ultimatum_10.12.27_22-15_zdf_105_TVOON_DE.mpg.avi");
        createFile(b);
        OtrCopy otrCopy = new OtrCopy();
        otrCopy.setCutDir(cutDir.getAbsolutePath());
        otrCopy.setDestDir(destDir.getAbsolutePath());
        otrCopy.setOriginalsDir(originalsDir.getAbsolutePath());
        otrCopy.copy();
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

    private void createFile(File file) throws FileNotFoundException, IOException {
        file.getParentFile().mkdirs();
        FileOutputStream out = new FileOutputStream(file);
        for (int i = 0; i < 10000; i++) {
            out.write('A' + i % 26);
        }
        out.close();
    }
}
