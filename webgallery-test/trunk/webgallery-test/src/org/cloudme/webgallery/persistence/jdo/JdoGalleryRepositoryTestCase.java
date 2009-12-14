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
        repository.delete(gallery.getId());
        assertPersistedGalleries(repository, 0);
    }
    
    @Test
    public void testPersistWithPhoto() {
        JdoGalleryRepository repository = initGalleryRepository();
        Gallery gallery = createGallery(repository, "Test Gallery");
        Collection<Gallery> galleries = assertPersistedGalleries(repository, 1);
        gallery = galleries.iterator().next();
        Photo photo = new Photo();
        photo.setName("Test Photo");
        gallery.addPhoto(photo);
        repository.save(gallery);
        JdoPhotoRepository photoRepository = new JdoPhotoRepository();
		photoRepository.setPersistenceManagerFactory(PMF.get());
		// photoRepository.init(PMF.get());
        Collection<Photo> photos = photoRepository.findAll();
        assertEquals(1, photos.size());
        assertEquals("Test Photo", photos.iterator().next().getName());
        System.out.println("Photo ID:" + photos.iterator().next().getId());
        galleries = repository.findAll();
        gallery = galleries.iterator().next();
        System.out.println("Gallery ID:" + gallery.getId());
        assertEquals("Test Photo", gallery.getPhotos().get(0).getName());
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
