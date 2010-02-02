package org.cloudme.webgallery.model.migration;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.cloudme.webgallery.Album;
import org.cloudme.webgallery.Photo;
import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.model.PhotoData;
import org.cloudme.webgallery.persistence.jdo.OldJdoAlbumRepository;
import org.cloudme.webgallery.persistence.jdo.JdoPhotoDataRepository;
import org.cloudme.webgallery.persistence.jdo.JdoAlbumRepository;
import org.cloudme.webgallery.persistence.jdo.JdoPhotoRepository;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.LocalDatastoreTestCase;
import com.google.appengine.tools.development.PMF;


public class MigrationServiceTest extends LocalDatastoreTestCase {
    private MigrationService service;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        service = new MigrationService();
        
        MigrationFlagRepository migrationFlagRepository = new MigrationFlagRepository();
        migrationFlagRepository.setPersistenceManagerFactory(PMF.get());
        service.migrationFlagRepository = migrationFlagRepository;
        
        OldJdoAlbumRepository albumRepository = new OldJdoAlbumRepository();
        albumRepository.setPersistenceManagerFactory(PMF.get());
        service.albumRepository = albumRepository;
        
        JdoAlbumRepository newAlbumRepository = new JdoAlbumRepository();
        newAlbumRepository.setPersistenceManagerFactory(PMF.get());
        service.newAlbumRepository = newAlbumRepository;
        
        JdoPhotoRepository newPhotoRepository = new JdoPhotoRepository();
        newPhotoRepository.setPersistenceManagerFactory(PMF.get());
        service.newPhotoRepository = newPhotoRepository;
        
        JdoPhotoDataRepository jdoPhotoDataRepository = new JdoPhotoDataRepository();
        jdoPhotoDataRepository.setPersistenceManagerFactory(PMF.get());
        service.jdoPhotoDataRepository = jdoPhotoDataRepository;
    }
    
    @Test
    public void testMigrate() {
        // create test data
        Album album = new Album();
        album.setDescription("Test description");
        album.setName("Test name");
        service.albumRepository.save(album);
        Photo photo = new Photo();
        photo.setContentType(ContentType.JPEG.toString());
        photo.setDataAsArray("Hello World".getBytes());
        photo.setFileName("test.jpg");
        photo.setName("Test photo");
        photo.setSize(11);
        album.addPhoto(photo);
        service.albumRepository.save(album);
        
        String log = service.migrate();
        System.out.println(log);
        
        // validate
        Collection<org.cloudme.webgallery.model.Album> albums = service.newAlbumRepository.findAll();
        assertEquals(1, albums.size());
        org.cloudme.webgallery.model.Album album3 = albums.iterator().next();
        
        Collection<org.cloudme.webgallery.model.Photo> photos = service.newPhotoRepository.findAll();
        assertEquals(1, photos.size());
        org.cloudme.webgallery.model.Photo photo2 = photos.iterator().next();
        
        assertEquals(album3.getId(), photo2.getAlbumId());
        
        Collection<PhotoData> photoDatas = service.jdoPhotoDataRepository.findAll();
        assertEquals(1, photoDatas.size());
        PhotoData photoData = photoDatas.iterator().next();
        
        assertEquals(photo2.getId(), photoData.getId());
        assertEquals("Hello World", new String(photoData.getDataAsArray()));
    }
}
