package org.cloudme.webgallery.stripes.action;

import java.util.Collection;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.webgallery.model.Album;
import org.cloudme.webgallery.model.Photo;
import org.cloudme.webgallery.service.AlbumService;
import org.cloudme.webgallery.service.PhotoService;

import com.google.inject.Inject;

@UrlBinding("/gallery/album/{albumId}/{$event}/{photoId}")
public class AlbumActionBean extends AbstractActionBean {
    @Inject
    private AlbumService albumService;
    @Inject
    private PhotoService photoService;
	private Long albumId;
	private Long photoId;

	@DefaultHandler
	public Resolution album() {
		return new ForwardResolution(getJspPath("/gallery/album"));
	}
	
	public Resolution photo() {
	    return new ForwardResolution(getJspPath("/gallery/photo"));
	}

	public Collection<Album> getAlbums() {
		return albumService.findAll();
	}

	public Collection<Photo> getPhotos() {
		return photoService.findByAlbumId(albumId);
	}

	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}

	public Long getAlbumId() {
		return albumId;
	}
	
	public Album getAlbum() {
	    return albumService.find(albumId);
	}
	
	public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

	public Long getPhotoId() {
        return photoId;
    }
    
    public Photo getPhoto() {
        return photoService.find(photoId);
    }
}
