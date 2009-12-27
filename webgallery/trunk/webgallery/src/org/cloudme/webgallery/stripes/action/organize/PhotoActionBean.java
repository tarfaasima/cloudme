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
import org.cloudme.webgallery.service.AlbumService;
import org.cloudme.webgallery.service.PhotoService;
import org.cloudme.webgallery.stripes.util.AbstractActionBean;

@UrlBinding("/organize/photo/{albumId}/{$event}/{id}")
public class PhotoActionBean extends AbstractActionBean {
    @SpringBean
    private AlbumService albumService;
    @SpringBean
    private PhotoService photoService;
    private FileBean photoFile;
    private String photoId;
    private String albumId;
    private Album album;

    public void setPhotoFile(FileBean photoFile) {
        this.photoFile = photoFile;
    }

    public Resolution upload() throws IOException {
        Photo photo = new Photo();
        photo.setFileName(photoFile.getFileName());
        photo.setContentType(photoFile.getContentType());
        photo.setSize(photoFile.getSize());
        photo.setDataAsArray(IOUtils.toByteArray(photoFile.getInputStream()));
        album.addPhoto(photo);
        // Image image = ImagesServiceFactory.makeImage(photo.getDataAsArray());
        // System.out.println(image.getWidth() + " x " + image.getHeight());
        // System.out.println(image.getFormat());
        albumService.save(album);
        return new RedirectResolution("/organize/photo/" + albumId);
    }

    public Resolution delete() {
        photoService.delete(photoId);
        return new RedirectResolution("/organize/photo/" + albumId);
    }
    
    public Resolution save() {
        albumService.save(album);
        return new RedirectResolution("/organize/photo/" + albumId);
    }

    @DefaultHandler
    public Resolution edit() {
        return new ForwardResolution(getJspPath("/organize/photo"));
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
        album = albumService.find(albumId);
    }

    public void setId(String photoId) {
        this.photoId = photoId;
    }

    public Album getAlbum() {
        return album;
    }
}
