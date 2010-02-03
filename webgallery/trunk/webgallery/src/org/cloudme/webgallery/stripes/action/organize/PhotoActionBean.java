package org.cloudme.webgallery.stripes.action.organize;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.cloudme.webgallery.model.Photo;
import org.cloudme.webgallery.service.PhotoService;
import org.cloudme.webgallery.stripes.action.AbstractActionBean;
import org.cloudme.webgallery.stripes.action.organize.upload.UploadManager;
import org.cloudme.webgallery.util.CollectionUtils;

@UrlBinding("/organize/photo/{albumId}/{$event}/{id}")
public class PhotoActionBean extends AbstractActionBean {
	@SpringBean
	private PhotoService photoService;
	private FileBean photoFile;
	private Long photoId;
	private Long albumId;
	private List<Photo> photos;

	public void setPhotoFile(FileBean photoFile) {
		this.photoFile = photoFile;
	}

	public Resolution upload() throws IOException {
		UploadManager manager = new UploadManager();
		Collection<Photo> photos = manager.upload(photoFile);
		photoService.save(albumId, photos);
		return new RedirectResolution("/organize/photo/" + albumId);
	}

	public Resolution delete() {
		photoService.delete(photoId);
		return new RedirectResolution("/organize/photo/" + albumId);
	}

	public Resolution save() {
		for (Photo photo : photos) {
			photo.setAlbumId(albumId);
			photoService.save(photo);
		}
		return new RedirectResolution("/organize/photo/" + albumId);
	}

	@DefaultHandler
	public Resolution edit() {
		return new ForwardResolution(getJspPath("/organize/photo"));
	}

	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
		photos = CollectionUtils.asList(photoService.findByAlbumId(albumId));
	}

	public Long getAlbumId() {
		return albumId;
	}

	public void setId(Long photoId) {
		this.photoId = photoId;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public List<Photo> getPhotos() {
		return photos;
	}
}
