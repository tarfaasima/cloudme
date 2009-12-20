package org.cloudme.webgallery.persistence.jdo;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.cloudme.webgallery.Gallery;
import org.cloudme.webgallery.Photo;
import org.junit.Test;

import com.google.appengine.tools.development.LocalDatastoreTestCase;
import com.google.appengine.tools.development.PMF;


public class JdoGalleryRepositoryTestCase extends LocalDatastoreTestCase {
    @Test
    public void testPersistGallery() {
        JdoGalleryRepository repository = initGalleryRepository();
        assertPersistedGalleries(repository, 0);
        Gallery gallery = createGallery(repository, "Test");
        Collection<Gallery> galleries = assertPersistedGalleries(repository, 1);
        gallery = galleries.iterator().next();
        assertEquals("Test", gallery.getName());
        gallery.setName("Hamburg");
        repository.save(gallery);
        galleries = assertPersistedGalleries(repository, 1);
        gallery = galleries.iterator().next();
        assertEquals("Hamburg", gallery.getName());
        System.out.println(gallery.getId());
        System.out.println(repository.find(gallery.getId()).getId());
        repository.delete(gallery.getId());
        assertPersistedGalleries(repository, 0);
    }
    
    @Test
    public void testPersistWithPhoto() {
        Gallery gallery = new Gallery();
        gallery.setName("Test Gallery");
        Photo photo = new Photo();
        photo.setName("Test Photo");
        gallery.addPhoto(photo);
        JdoGalleryRepository repository = initGalleryRepository();
        repository.save(gallery);
        Collection<Gallery> galleries = repository.findAll();
        assertEquals(1, galleries.size());
        Gallery gallery2 = galleries.iterator().next();
        assertEquals("Test Gallery", gallery2.getName());
        assertEquals(1, gallery2.getPhotos().size());
        Gallery gallery3 = repository.find(gallery2.getId());
        assertEquals("Test Gallery", gallery3.getName());
        assertEquals(1, gallery3.getPhotos().size());
        assertEquals("Test Photo", gallery3.getPhotos().get(0).getName());
        JdoPhotoRepository photoRepo = new JdoPhotoRepository();
        photoRepo.setPersistenceManagerFactory(PMF.get());
        assertEquals(1, photoRepo.findAll().size());
        repository.delete(gallery3.getId());
        assertEquals(0, repository.findAll().size());
        assertEquals(0, photoRepo.findAll().size());
    }
    
    private Collection<Gallery> assertPersistedGalleries(JdoGalleryRepository repository, int expected) {
        Collection<Gallery> galleries = repository.findAll();
        assertEquals(expected, galleries.size());
        return galleries;
    }

    private Gallery createGallery(JdoGalleryRepository repository, String name) {
        Gallery gallery = new Gallery();
        gallery.setName(name);
        repository.save(gallery);
        return gallery;
    }

    private JdoGalleryRepository initGalleryRepository() {
        JdoGalleryRepository repository = new JdoGalleryRepository();
		repository.setPersistenceManagerFactory(PMF.get());
		// repository.init(PMF.get());
        return repository;
    }
}
