package org.cloudme.webgallery.service;

import java.util.Collection;

import javax.cache.CacheException;

import org.cloudme.webgallery.model.Photo;
import org.cloudme.webgallery.persistence.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoService extends AbstractService<Long, Photo> {
	private final PhotoRepository photoRepository;

	@Autowired
	protected PhotoService(PhotoRepository repository) throws CacheException {
        super(repository);
		photoRepository = repository;
    }

	public Collection<Photo> findByAlbumId(Long albumId) {
		return photoRepository.findByAlbumId(albumId);
	}
}
