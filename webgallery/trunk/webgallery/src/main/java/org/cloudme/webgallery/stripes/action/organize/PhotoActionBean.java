package org.cloudme.webgallery.stripes.action.organize;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.webgallery.message.Message;
import org.cloudme.webgallery.model.Photo;
import org.cloudme.webgallery.service.FlickrService;
import org.cloudme.webgallery.service.PhotoService;
import org.cloudme.webgallery.stripes.action.AbstractActionBean;
import org.cloudme.webgallery.stripes.action.organize.upload.UploadManager;
import org.cloudme.webgallery.util.CollectionUtils;

import com.google.inject.Inject;

@UrlBinding("/organize/photo/{albumId}/{$event}/{id}")
public class PhotoActionBean extends AbstractActionBean {
    @Inject
    private PhotoService photoService;
    @Inject
    private FlickrService flickrService;
	private FileBean photoFile;
	private Long photoId;
	private Long albumId;
	private List<Photo> photos;

	public void setPhotoFile(FileBean photoFile) {
		this.photoFile = photoFile;
	}

	public Resolution upload() throws IOException {
		UploadManager manager = new UploadManager();
		photos = CollectionUtils.asList(manager.upload(photoFile));
        photoService.save(albumId, photos);
        addMessage(new Message("{0} photos uploaded successfully.", photos.size()));
		return new RedirectResolution("/organize/photo/" + albumId);
	}

	public Resolution delete() {
		photoService.delete(albumId, photoId);
		addMessage(new Message("Photo deleted successfully."));
		return new RedirectResolution("/organize/photo/" + albumId);
	}

    public Resolution save() {
        photoService.save(albumId, photos);
        addMessage(new Message("Photo saved successfully."));
        return new RedirectResolution("/organize/photo/" + albumId);
    }
    
    public Resolution flickr() {
        HttpServletRequest req = getContext().getRequest();
        addMessage(flickrService.post(photoId, req.getServerName(), req.getServerPort()));
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
