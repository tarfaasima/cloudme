package org.cloudme.webgallery.stripes.action;

import java.util.Collection;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.webgallery.model.Album;
import org.cloudme.webgallery.service.AlbumService;
import org.cloudme.webgallery.service.PhotoService;

import com.google.inject.Inject;

@UrlBinding("/gallery/home")
public class HomeActionBean extends AbstractActionBean {
    @Inject
    private AlbumService albumService;
    @Inject
    private PhotoService photoService;
    
    @DefaultHandler
    public Resolution show() {
        return new ForwardResolution(getJspPath("/gallery/home"));
    }
    
    public long getRandomPhotoId() {
        return photoService.getRandomPhotoId();
    }

	public Collection<Album> getAlbums() {
		return albumService.findAll();
	}
	
	public Long getAlbumId() {
	    return null;
	}
}
