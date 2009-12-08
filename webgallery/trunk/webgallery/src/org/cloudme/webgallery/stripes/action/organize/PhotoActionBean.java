package org.cloudme.webgallery.stripes.action.organize;

import java.io.IOException;

import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.apache.commons.io.IOUtils;
import org.cloudme.webgallery.Photo;
import org.cloudme.webgallery.service.GenericService;
import org.cloudme.webgallery.stripes.util.AbstractActionBean;

@UrlBinding("/p/organize/photo")
public class PhotoActionBean extends AbstractActionBean {
    @SpringBean
    private GenericService<Photo> service;
    private Photo photo;
    
    public void setPhotoFile(FileBean photoFile) throws IOException {
        System.out.println(photoFile.getFileName());
        System.out.println(IOUtils.toByteArray(photoFile.getInputStream()).length);
        System.out.println(photoFile.getContentType());
        System.out.println(photoFile.getSize());
        photo = new Photo();
        photo.setImageDataAsArray(IOUtils.toByteArray(photoFile.getInputStream()));
    }
    
    public Resolution upload() {
        System.out.println("Upload complete.");
        service.save(photo);
        return new RedirectResolution(IndexActionBean.class);
    }
}
