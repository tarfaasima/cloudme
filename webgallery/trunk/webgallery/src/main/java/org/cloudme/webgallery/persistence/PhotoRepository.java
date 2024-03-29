package org.cloudme.webgallery.persistence;

import java.util.Collection;

import org.cloudme.webgallery.model.Photo;


public interface PhotoRepository extends Repository<Long, Photo> {
	Collection<Photo> findByAlbumId(Long albumId);

	int countPhotosByAlbumId(Long albumId);

    Long getRandomPhotoId();
}
