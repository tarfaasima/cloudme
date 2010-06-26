package org.cloudme.webgallery.service;

import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.cloudme.webgallery.guice.WebgalleryModule;
import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.image.DefaultImageFormat;
import org.cloudme.webgallery.image.ImageFormat;
import org.cloudme.webgallery.model.Photo;
import org.cloudme.webgallery.model.PhotoData;
import org.cloudme.webgallery.persistence.objectify.ObjectifyPhotoDataRepository;
import org.cloudme.webgallery.persistence.objectify.ObjectifyPhotoRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class PhotoDataServiceTest {
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    private PhotoDataService pds;
    private PhotoService ps;
    private ScaledPhotoDataService spds;

    @Before
    public void setUp() throws Exception {
        helper.setUp();
        pds = new PhotoDataService(new ObjectifyPhotoDataRepository());
        ps = new PhotoService(new ObjectifyPhotoRepository());
        spds = new ScaledPhotoDataService();

        Injector injector = Guice.createInjector(new WebgalleryModule());
        injector.injectMembers(pds);
        injector.injectMembers(ps);
        injector.injectMembers(spds);
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void testGenerate() throws Exception {
        Photo photo = new Photo();
        photo.setCropBalance(0.5F);
        ps.save(photo);
        Long photoId = photo.getId();
        PhotoData photoData = new PhotoData();
        FileInputStream in = new FileInputStream("src/main/webapp/images/over_quota_894.jpg");
        byte[] data = IOUtils.toByteArray(in);
        in.close();
        photoData.setDataAsArray(data);
        photoData.setId(photoId);
        pds.save(photoData);
        pds.generate(photoId, ContentType.JPEG);
        for (ImageFormat format : DefaultImageFormat.values()) {
            byte[] scaledPhotoData = spds.find(photoId,
                    format,
                    ContentType.JPEG);
            assertNotNull(format.toString() + " not available.",
                    scaledPhotoData);
        }
    }
}
