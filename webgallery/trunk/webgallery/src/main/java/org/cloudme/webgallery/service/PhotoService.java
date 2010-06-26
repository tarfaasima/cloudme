package org.cloudme.webgallery.service;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.cloudme.webgallery.model.Album;
import org.cloudme.webgallery.model.Photo;
import org.cloudme.webgallery.model.PhotoData;
import org.cloudme.webgallery.persistence.PhotoRepository;

import com.google.apphosting.api.ApiProxy.RequestTooLargeException;
import com.google.inject.Inject;

public class PhotoService extends AbstractService<Long, Photo, PhotoRepository> {
    @Inject
    private PhotoDataService photoDataService;
    @Inject
    private AlbumService albumService;
    @Inject
    private ScaledPhotoDataService scaledPhotoDataService;
    private static final Logger LOG = Logger.getLogger(PhotoService.class);

    @Inject
    public PhotoService(PhotoRepository repository) {
        super(repository);
    }

    /**
     * Returns all {@link Photo}s accociated with the given {@link Album} ID.
     * 
     * @param albumId
     *            The ID of the {@link Album}.
     * @return The {@link Photo}s, that are associated with the {@link Album}.
     */
    public Collection<Photo> findByAlbumId(Long albumId) {
        return repository.findByAlbumId(albumId);
    }

    /**
     * Saves the {@link Photo}s for the given {@link Album} ID. If
     * {@link Photo#getPhotoData()} is not null, then the {@link PhotoData} is
     * saved. If the {@link PhotoData} is too large, the {@link Photo} will not
     * be saved. The {@link Album#getPhotoCount()} will be updated.
     * 
     * @param albumId
     *            The ID of the {@link Album}.
     * @param photos
     *            The {@link Photo}s that are saved.
     */
    public void save(Long albumId, Collection<Photo> photos) {
        for (Photo photo : photos) {
            photo.setAlbumId(albumId);
            save(photo);
            PhotoData photoData = photo.getPhotoData();
            if (photoData != null) {
                photoData.setId(photo.getId());
                try {
                    photoDataService.save(photoData);
                }
                catch (RequestTooLargeException e) {
                    LOG.warn("Photo data too large: " + photo.getFileName() + " (" + photoData.getDataAsArray().length + " bytes)", e);
                    super.delete(photo.getId());
                }
            }
        }
        updatePhotoCount(albumId);
    }
    
    private void updatePhotoCount(Long albumId) {
        int count = repository.countPhotosByAlbumId(albumId);
        albumService.updatePhotoCount(albumId, count);
    }
    
    /**
     * Deletes the {@link Photo} and the {@link PhotoData}. If the
     * {@link PhotoData} cannot be deleted, the {@link Photo} will not be
     * deleted.
     */
    @Override
    public void delete(Long id) {
        photoDataService.delete(id);
        scaledPhotoDataService.deleteByPhotoId(id);
        super.delete(id);
    }

    /**
     * Deletes the {@link Photo} and updates the {@link Album#getPhotoCount()}.
     * 
     * @param albumId
     *            The ID of the {@link Album} of the {@link Photo}.
     * @param photoId
     *            The ID of the {@link Photo}.
     */
    public void delete(Long albumId, Long photoId) {
        delete(photoId);
        updatePhotoCount(albumId);
    }
}
