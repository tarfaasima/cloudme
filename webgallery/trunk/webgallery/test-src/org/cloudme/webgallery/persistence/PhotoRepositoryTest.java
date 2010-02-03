package org.cloudme.webgallery.persistence;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.cloudme.webgallery.model.Album;
import org.cloudme.webgallery.model.Photo;
import org.cloudme.webgallery.persistence.jdo.JdoAlbumRepository;
import org.cloudme.webgallery.persistence.jdo.JdoPhotoRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.jdo.support.JdoDaoSupport;

import com.google.appengine.tools.development.LocalDatastoreTestCase;
import com.google.appengine.tools.development.PMF;

public class PhotoRepositoryTest extends LocalDatastoreTestCase {
	private AlbumRepository albumRepository;
	private PhotoRepository photoRepository;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		albumRepository = new JdoAlbumRepository();
		photoRepository = new JdoPhotoRepository();
		setPMF(albumRepository, photoRepository);
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

	private void create(Album album, Photo... photos) {
		albumRepository.save(album);
		for (Photo photo : photos) {
			photo.setAlbumId(album.getId());
			photoRepository.save(photo);
		}
	}

	private void setPMF(Repository<?, ?>... repositories) {
		for (Repository<?, ?> repository : repositories) {
			if (repository instanceof JdoDaoSupport) {
				JdoDaoSupport jdoDaoSupport = (JdoDaoSupport) repository;
				jdoDaoSupport.setPersistenceManagerFactory(PMF.get());
			}
		}
	}
}
