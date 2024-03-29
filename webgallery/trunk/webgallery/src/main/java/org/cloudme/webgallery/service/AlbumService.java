package org.cloudme.webgallery.service;

import org.cloudme.webgallery.model.Album;
import org.cloudme.webgallery.model.Photo;
import org.cloudme.webgallery.persistence.AlbumRepository;

import com.google.inject.Inject;

public class AlbumService extends AbstractService<Long, Album, AlbumRepository> {
    @Inject
    private PhotoService photoService;

    @Inject
    protected AlbumService(AlbumRepository repository) {
        super(repository);
	}

	/**
	 * Updates the {@link Album#getPhotoCount()}.
	 * 
	 * @param albumId
	 *            The ID of the {@link Album}.
	 * @param count
	 *            The number of {@link Photo}s of this {@link Album}.
	 */
	public void updatePhotoCount(Long albumId, int count) {
		Album album = repository.find(albumId);
		album.setPhotoCount(count);
		repository.save(album);
	}

	/**
	 * Deletes the {@link Album} and all associated {@link Photo}s. If a
	 * {@link Photo} cannot be deleted, the {@link Album} will not be deleted.
	 */
	@Override
	public void delete(Long id) {
		for (Photo photo : photoService.findByAlbumId(id)) {
			photoService.delete(photo.getId());
		}
		super.delete(id);
	}
}
