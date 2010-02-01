package org.cloudme.webgallery.model.migration;

import java.util.List;

import org.cloudme.webgallery.Photo;
import org.cloudme.webgallery.model.Album;

public class AlbumMigrator {
    private final PhotoMigrator photoMigrator = new PhotoMigrator();
    
    public void migrate(NewAlbumRepository newAlbumRepository, NewPhotoRepository newPhotoRepository, NewPhotoDataRepository newPhotoDataRepository, String description, String name, List<Photo> photos) {
        Album album = new Album();
        album.setDescription(description);
        album.setName(name);
        newAlbumRepository.save(album);
        for (Photo photo : photos) {
            photoMigrator.migrate(newPhotoRepository, newPhotoDataRepository, album.getId(), photo.getContentType(), photo.getDataAsArray(), photo.getFileName(), photo.getName(), photo.getSize());
        }
    }
}
