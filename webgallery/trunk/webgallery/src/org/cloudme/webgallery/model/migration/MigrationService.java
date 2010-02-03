package org.cloudme.webgallery.model.migration;

import java.util.Collection;

import org.cloudme.webgallery.Album;
import org.cloudme.webgallery.persistence.OldAlbumRepository;
import org.cloudme.webgallery.persistence.jdo.JdoAlbumRepository;
import org.cloudme.webgallery.persistence.jdo.JdoPhotoDataRepository;
import org.cloudme.webgallery.persistence.jdo.JdoPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("deprecation")
@Service
public class MigrationService {
	@Autowired
	OldAlbumRepository albumRepository;
	private final AlbumMigrator albumMigrator = new AlbumMigrator();
	@Autowired
	JdoAlbumRepository newAlbumRepository;
	@Autowired
	JdoPhotoRepository newPhotoRepository;
	@Autowired
	JdoPhotoDataRepository jdoPhotoDataRepository;
	private final StringBuilder log = new StringBuilder();

	public synchronized String migrate() {
		try {
			Collection<Album> albums = albumRepository.findAll();
			for (Album album : albums) {
				albumMigrator.migrate(log, newAlbumRepository, newPhotoRepository, jdoPhotoDataRepository, album
						.getDescription(), album.getName(), album.getPhotos());
				albumRepository.delete(album.getId());
			}
		} catch (Exception e) {
			log.append(e.getMessage());
		}
		return log.toString();
	}
}
