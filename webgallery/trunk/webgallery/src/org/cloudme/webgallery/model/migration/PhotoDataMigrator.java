package org.cloudme.webgallery.model.migration;

import org.cloudme.webgallery.model.PhotoData;

public class PhotoDataMigrator {
    public void migrate(NewPhotoDataRepository newPhotoDataRepository, Long id, byte[] data) {
        PhotoData photoData = new PhotoData();
        photoData.setId(id);
        photoData.setDataAsArray(data);
        newPhotoDataRepository.save(photoData);
    }
}
