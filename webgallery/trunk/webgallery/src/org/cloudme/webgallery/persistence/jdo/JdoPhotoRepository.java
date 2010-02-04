package org.cloudme.webgallery.persistence.jdo;

import java.util.Collection;

import org.cloudme.webgallery.model.Photo;
import org.cloudme.webgallery.persistence.PhotoRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JdoPhotoRepository extends AbstractJdoRepository<Long, Photo> implements PhotoRepository {
    public JdoPhotoRepository() {
        super(Photo.class);
    }

	public Collection<Photo> findByAlbumId(Long albumId) {
		return queryCollection("albumId == " + albumId);
	}

	public int countPhotosByAlbumId(final Long albumId) {
		return findByAlbumId(albumId).size();
	}
}
