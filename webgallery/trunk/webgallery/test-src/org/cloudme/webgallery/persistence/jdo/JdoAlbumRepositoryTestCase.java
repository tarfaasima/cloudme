package org.cloudme.webgallery.persistence.jdo;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.cloudme.webgallery.Album;
import org.cloudme.webgallery.Photo;
import org.junit.Test;

import com.google.appengine.tools.development.PMF;


public class JdoAlbumRepositoryTestCase extends AbstractJdoTestCase<String, Album> {
    @Override
    public Album createEntity() {
        Album album = new Album();
        album.setName("Album Name");
        album.setDescription("Album Description");
        return album;
    }
    
    @Override
    public AbstractJdoRepository<String, Album> createRepository() {
        return new OldJdoAlbumRepository();
    }
    
    @Test
    public void testPersistWithPhoto() {
        Album album = createEntity();
        Photo photo = new Photo();
        photo.setName("Test Photo");
        photo.setDataAsArray(new byte[100000]);
        album.addPhoto(photo);
        repo.save(album);
        Collection<Album> albums = repo.findAll();
        assertRepoSize(1);

        Album album2 = albums.iterator().next();
        assertEquals("Album Name", album2.getName());
        assertEquals(1, album2.getPhotos().size());
        
        Album album3 = repo.find(album2.getId());
        assertEquals("Album Name", album3.getName());
        assertEquals(1, album3.getPhotos().size());
        Photo photo2 = album3.getPhotos().get(0);
        assertEquals("Test Photo", photo2.getName());
        
        OldJdoPhotoRepository photoRepo = new OldJdoPhotoRepository();
        photoRepo.setPersistenceManagerFactory(PMF.get());
        assertEquals(1, photoRepo.findAll().size());
        
        Photo photo3 = photoRepo.find(photo2.getId());
        assertEquals("Test Photo", photo3.getName());
        assertEquals(100000, photo3.getDataAsArray().length);
        
        repo.delete(album3.getId());
        assertRepoSize(0);
        assertEquals(0, photoRepo.findAll().size());
    }
    
    @Test
    public void testDeletePhoto() {
        Album album1 = createEntity();
        repo.save(album1);
        
        Album album2 = repo.findAll().iterator().next();
        Photo photo1 = new Photo();
        photo1.setName("Photo 1");
        album2.addPhoto(photo1);
        Photo photo2 = new Photo();
        photo2.setName("Photo 2");
        album2.addPhoto(photo2);
        repo.save(album2);
        
        Album album3 = repo.findAll().iterator().next();
        assertEquals(2, album3.getPhotos().size());
        Photo photo3 = album3.getPhotos().get(0);
        OldJdoPhotoRepository photoRepo = new OldJdoPhotoRepository();
        photoRepo.setPersistenceManagerFactory(PMF.get());
        photoRepo.delete(photo3.getId());
        
        Album album4 = repo.findAll().iterator().next();
        assertEquals(1, album4.getPhotos().size());
    }
}
