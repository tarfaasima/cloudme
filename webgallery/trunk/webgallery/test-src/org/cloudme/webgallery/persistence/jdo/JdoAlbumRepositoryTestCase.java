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
        return new JdoAlbumRepository();
    }
    
    @Test
    public void testPersistGallery() {
        JdoAlbumRepository repository = initAlbumRepository();
        assertPersistedAlbums(repository, 0);
        Album album = createAlbum(repository, "Test");
        Collection<Album> albums = assertPersistedAlbums(repository, 1);
        album = albums.iterator().next();
        assertEquals("Test", album.getName());
        album.setName("Hamburg");
        repository.save(album);
        albums = assertPersistedAlbums(repository, 1);
        album = albums.iterator().next();
        assertEquals("Hamburg", album.getName());
        repository.delete(album.getId());
        assertPersistedAlbums(repository, 0);
    }
    
    @Test
    public void testPersistWithPhoto() {
        Album album = new Album();
        album.setName("Test Album");
        Photo photo = new Photo();
        photo.setName("Test Photo");
        album.addPhoto(photo);
        JdoAlbumRepository repository = initAlbumRepository();
        repository.save(album);
        Collection<Album> albums = repository.findAll();
        assertEquals(1, albums.size());
        Album album2 = albums.iterator().next();
        assertEquals("Test Album", album2.getName());
        assertEquals(1, album2.getPhotos().size());
        Album album3 = repository.find(album2.getId());
        assertEquals("Test Album", album3.getName());
        assertEquals(1, album3.getPhotos().size());
        assertEquals("Test Photo", album3.getPhotos().get(0).getName());
        JdoPhotoRepository photoRepo = new JdoPhotoRepository();
        photoRepo.setPersistenceManagerFactory(PMF.get());
        assertEquals(1, photoRepo.findAll().size());
        repository.delete(album3.getId());
        assertEquals(0, repository.findAll().size());
        assertEquals(0, photoRepo.findAll().size());
    }
    
    private Collection<Album> assertPersistedAlbums(JdoAlbumRepository repository, int expected) {
        Collection<Album> albums = repository.findAll();
        assertEquals(expected, albums.size());
        return albums;
    }

    private Album createAlbum(JdoAlbumRepository repository, String name) {
        Album album = new Album();
        album.setName(name);
        repository.save(album);
        return album;
    }

    private JdoAlbumRepository initAlbumRepository() {
        JdoAlbumRepository repository = new JdoAlbumRepository();
		repository.init(PMF.get());
        return repository;
    }

}
