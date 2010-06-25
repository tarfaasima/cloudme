package org.cloudme.webgallery.persistence.jdo;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.cloudme.webgallery.model.Album;
import org.cloudme.webgallery.model.Photo;
import org.cloudme.webgallery.persistence.AlbumRepository;
import org.cloudme.webgallery.persistence.PhotoRepository;
import org.cloudme.webgallery.persistence.Repository;
import org.cloudme.webgallery.persistence.objectify.ObjectifyAlbumRepository;
import org.cloudme.webgallery.persistence.objectify.ObjectifyPhotoRepository;
import org.junit.Before;
import org.junit.Test;

public class PhotoRepositoryTest extends AbstractDatastoreTestCase<Long, Photo> {
    private AlbumRepository albumRepository;
    private PhotoRepository photoRepository;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        albumRepository = new ObjectifyAlbumRepository();
        photoRepository = new ObjectifyPhotoRepository();
    }

    @Test
    public void testFindByAlbumId() {
        Album a1 = new Album();
        Photo p1 = new Photo();
        create(a1, p1);
        Album a2 = new Album();
        Photo p2 = new Photo();
        create(a2, p2);

        Collection<Photo> photos = photoRepository.findByAlbumId(a1.getId());
        assertEquals(1, photos.size());
        assertEquals(p1.getId(), photos.iterator().next().getId());
    }

	@Test
	public void testCountPhotosByAlbumId() {
		Album a1 = new Album();
		Photo p1 = new Photo();
		create(a1, p1);
		Album a2 = new Album();
		Photo p2 = new Photo();
		create(a2, p2);

		int count = photoRepository.countPhotosByAlbumId(a1.getId());
		assertEquals(1, count);
	}

    private void create(Album album, Photo... photos) {
        albumRepository.save(album);
        for (Photo photo : photos) {
            photo.setAlbumId(album.getId());
            photoRepository.save(photo);
        }
    }

    @Override
    public Photo createEntity() {
        return new Photo();
    }

    @Override
    public Repository<Long, Photo> createRepository() {
        return new ObjectifyPhotoRepository();
    }
}
