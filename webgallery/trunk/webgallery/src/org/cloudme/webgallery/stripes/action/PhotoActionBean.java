package org.cloudme.webgallery.stripes.action;

import java.io.IOException;

import javax.servlet.ServletOutputStream;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.cloudme.webgallery.service.PhotoService;
import org.cloudme.webgallery.stripes.util.AbstractActionBean;

@UrlBinding("/gallery/photo/{photoId}_{format}.{type}")
public class PhotoActionBean extends AbstractActionBean {
    private String photoId;
    private String format;
    private String type;
    @SpringBean
    private PhotoService service;
    
    @DefaultHandler
    public void show() throws IOException {
        ServletOutputStream out = getContext().getResponse().getOutputStream();
        out.write(service.getPhotoData(photoId, format, type));
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setType(String type) {
        this.type = type;
    }
}
