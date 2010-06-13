package org.cloudme.webgallery.image.gae;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import net.sourceforge.stripes.action.FileBean;

import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.image.DefaultImageFormat;
import org.cloudme.webgallery.model.Photo;
import org.cloudme.webgallery.model.PhotoData;
import org.cloudme.webgallery.stripes.action.organize.upload.UploadManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.tools.development.testing.LocalImagesServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;


public class GaeImageServiceTest  {
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalImagesServiceTestConfig());
    
    @Before
    public void setUp() {
        helper.setUp();
    }
    
    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void testProcess() throws IOException {
        assertProcess(loadPhoto(),
                DefaultImageFormat.MEDIUM,
                DefaultImageFormat.MEDIUM.getWidth(),
                DefaultImageFormat.MEDIUM.getHeight());
    }

	private void assertProcess(PhotoData photo, DefaultImageFormat format, int width, int height) {
        GaeImageService service = new GaeImageService();
        byte[] data = service.process(photo.getDataAsArray(), format, ContentType.JPEG, 0.5f);
        Image image = ImagesServiceFactory.makeImage(data);
        assertEquals(width, image.getWidth());
        assertEquals(height, image.getHeight());
    }

	private PhotoData loadPhoto() throws IOException {
        FileBean fileBean = new FileBean(null, "application/zip", "images.zip") {
            @Override
            public InputStream getInputStream() throws IOException {
                return ClassLoader.getSystemResourceAsStream(getFileName());
            }
        };
        Collection<Photo> photos = new UploadManager().upload(fileBean);
		return photos.iterator().next().getPhotoData();
    }
}
