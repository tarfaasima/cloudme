package org.cloudme.webgallery.model.migration;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.cloudme.webgallery.Photo;
import org.cloudme.webgallery.model.Album;
import org.cloudme.webgallery.persistence.jdo.JdoAlbumRepository;
import org.cloudme.webgallery.persistence.jdo.JdoPhotoDataRepository;
import org.cloudme.webgallery.persistence.jdo.JdoPhotoRepository;

@SuppressWarnings("deprecation")
public class AlbumMigrator {
    private final PhotoMigrator photoMigrator = new PhotoMigrator();
    
	public void migrate(StringBuilder log, JdoAlbumRepository newAlbumRepository,
			JdoPhotoRepository newPhotoRepository, JdoPhotoDataRepository jdoPhotoDataRepository, String description,
			String name, List<Photo> photos) {
        Album album = new Album();
        album.setDescription(description);
        album.setName(name);
        newAlbumRepository.save(album);
        for (Photo photo : photos) {
            photoMigrator.migrate(log, newPhotoRepository, jdoPhotoDataRepository, album.getId(), photo.getContentType(), photo.getDataAsArray(), photo.getFileName(), photo.getName(), photo.getSize());
        }
        log.append(ToStringBuilder.reflectionToString(album) + "\n");
    }
}
