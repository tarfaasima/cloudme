package org.cloudme.webgallery.model.migration;

import org.cloudme.webgallery.model.Photo;

public class PhotoMigrator {
    private final PhotoDataMigrator photoDataMigrator = new PhotoDataMigrator();
    
    public void migrate(NewPhotoRepository newPhotoRepository, NewPhotoDataRepository newPhotoDataRepository, Long albumId, String contentType, byte[] data, String fileName, String name, long size) {
        Photo photo = new Photo();
        photo.setAlbumId(albumId);
        photo.setContentType(contentType);
        photo.setFileName(fileName);
        photo.setName(name);
        photo.setSize(size);
        newPhotoRepository.save(photo);
        photoDataMigrator.migrate(newPhotoDataRepository, photo.getId(), data);
    }
}
