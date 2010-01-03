package org.cloudme.webgallery.stripes.action.organize.upload;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import net.sourceforge.stripes.action.FileBean;

import org.cloudme.webgallery.Photo;
import org.junit.Test;

public class UploadManagerTest {
    @Test
    public void testUploadZipFile() throws IOException {
        FileBean fileBean = new FileBean(null, "application/zip", "images.zip") {
            @Override
            public InputStream getInputStream() throws java.io.IOException {
                return ClassLoader.getSystemResourceAsStream(getFileName());
            }
        };
        Collection<Photo> photos = new UploadManager().upload(fileBean);
        assertEquals(2, photos.size());
    }
}
