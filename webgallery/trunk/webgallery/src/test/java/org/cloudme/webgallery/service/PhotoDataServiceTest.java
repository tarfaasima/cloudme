package org.cloudme.webgallery.service;

import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.cloudme.webgallery.cache.gae.GaeCacheService;
import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.image.DefaultImageFormat;
import org.cloudme.webgallery.image.ImageFormat;
import org.cloudme.webgallery.image.gae.GaeImageService;
import org.cloudme.webgallery.model.Photo;
import org.cloudme.webgallery.model.PhotoData;
import org.cloudme.webgallery.persistence.jdo.JdoPhotoDataRepository;
import org.cloudme.webgallery.persistence.jdo.JdoPhotoRepository;
import org.cloudme.webgallery.persistence.jdo.JdoScaledPhotoDataRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.PMF;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class PhotoDataServiceTest {
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    private PhotoDataService pds;
    private PhotoService ps;
    private ScaledPhotoDataService spds;

    @Before
    public void setUp() throws Exception {
        helper.setUp();
        pds = new PhotoDataService();
        pds.setCacheService(new GaeCacheService());
        pds.setImageService(new GaeImageService());
        JdoPhotoDataRepository photoDataRepository = new JdoPhotoDataRepository();
        photoDataRepository.setPersistenceManagerFactory(PMF.get());
        pds.setPhotoDataRepository(photoDataRepository);
        JdoPhotoRepository photoRepository = new JdoPhotoRepository();
        photoRepository.setPersistenceManagerFactory(PMF.get());
        ps = new PhotoService(photoRepository);
        pds.setPhotoService(ps);
        JdoScaledPhotoDataRepository spdr = new JdoScaledPhotoDataRepository();
        spdr.setPersistenceManagerFactory(PMF.get());
        spds = new ScaledPhotoDataService(spdr);
        pds.setScaledPhotoDataService(spds);
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
