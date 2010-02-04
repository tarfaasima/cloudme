package org.cloudme.webgallery.service;

import java.util.Collection;
import java.util.logging.Logger;

import javax.cache.CacheException;

import org.cloudme.webgallery.model.Photo;
import org.cloudme.webgallery.model.PhotoData;
import org.cloudme.webgallery.persistence.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.apphosting.api.ApiProxy.RequestTooLargeException;

@Service
public class PhotoService extends AbstractService<Long, Photo> {
    private final PhotoRepository photoRepository;
    @Autowired
    private PhotoDataService photoDataService;
	@Autowired
	private AlbumService albumService;
	private static final Logger LOGGER = Logger.getLogger(PhotoService.class.getName());

    @Autowired
    protected PhotoService(PhotoRepository repository) throws CacheException {
        super(repository);
        photoRepository = repository;
    }

    public Collection<Photo> findByAlbumId(Long albumId) {
        return photoRepository.findByAlbumId(albumId);
    }

    public void save(Long albumId, Collection<Photo> photos) {
        for (Photo photo : photos) {
            photo.setAlbumId(albumId);
            super.save(photo);
            PhotoData photoData = photo.getPhotoData();
            if (photoData != null) {
                photoData.setId(photo.getId());
				try {
					photoDataService.save(photoData);
				} catch (RequestTooLargeException e) {
					LOGGER.warning("Photo data too large: " + photo.getFileName() + " ("
							+ photoData.getDataAsArray().length + " bytes)");
					super.delete(photo.getId());
				}
            }
        }
		updatePhotoCount(albumId);
    }

	private void updatePhotoCount(Long albumId) {
		int count = photoRepository.countPhotosByAlbumId(albumId);
		albumService.updatePhotoCount(albumId, count);
	}

	public void delete(Long albumId, Long photoId) {
		delete(photoId);
		updatePhotoCount(albumId);
	}
}
