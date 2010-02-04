package org.cloudme.webgallery.service;

import org.cloudme.webgallery.model.Album;
import org.cloudme.webgallery.persistence.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumService extends AbstractService<Long, Album> {
	private final AlbumRepository repository;

	@Autowired
	protected AlbumService(AlbumRepository repository) {
        super(repository);
		this.repository = repository;
    }

	public void updatePhotoCount(Long albumId, int count) {
		Album album = repository.find(albumId);
		album.setPhotoCount(count);
		repository.save(album);
	}
}
