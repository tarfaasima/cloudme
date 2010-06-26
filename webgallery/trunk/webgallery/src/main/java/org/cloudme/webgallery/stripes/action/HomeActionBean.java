package org.cloudme.webgallery.stripes.action;

import static org.cloudme.webgallery.util.CollectionUtils.asList;

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

@UrlBinding("/gallery/home")
public class HomeActionBean extends AbstractActionBean {
    @Inject
	private PhotoService photoService;
    @Inject
	private AlbumService albumService;
    
    @DefaultHandler
    public Resolution show() {
        return new ForwardResolution(getJspPath("/gallery/home"));
    }
    
	public Long getRandomPhotoId() {
		Collection<Photo> photos = photoService.findAll();
		if (photos.isEmpty()) {
		    return null;
		}
        int index = (int) (Math.random() * photos.size());
		return asList(photos).get(index).getId();
    }
    
	public Collection<Album> getAlbums() {
		return albumService.findAll();
	}
	
	public Long getAlbumId() {
	    return null;
	}
}
