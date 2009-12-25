package org.cloudme.webgallery.stripes.action.organize;

import java.io.IOException;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.apache.commons.io.IOUtils;
import org.cloudme.webgallery.Album;
import org.cloudme.webgallery.Photo;
import org.cloudme.webgallery.service.GenericService;
import org.cloudme.webgallery.stripes.util.AbstractActionBean;

@UrlBinding("/organize/photo/{albumId}/${event}/{id}")
public class PhotoActionBean extends AbstractActionBean {
    private Album album;
    @SpringBean
	private GenericService<String, Album> service;

    public void setPhotoFile(FileBean photoFile) throws IOException {
        System.out.println(photoFile.getFileName());
        System.out.println(IOUtils.toByteArray(photoFile.getInputStream()).length);
        System.out.println(photoFile.getContentType());
        System.out.println(photoFile.getSize());
        Photo photo = new Photo();
//        photo.setImageDataAsArray(IOUtils.toByteArray(photoFile.getInputStream()));
        album.addPhoto(photo);
		service.save(album);
    }

    public Resolution upload() {
        return new RedirectResolution("/organize/photo/" + album.getId() + "/edit");
    }
    
    @DefaultHandler
    public Resolution edit() {
        return new ForwardResolution(getJspPath("/organize/photo"));
    }

    public void setAlbumId(String albumId) {
		Album album = service.find(albumId);
        setAlbum(album);
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Album getAlbum() {
        return album;
    }
}
