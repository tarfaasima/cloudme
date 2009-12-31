package org.cloudme.webgallery.stripes.action;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.image.ContentTypeFactory;
import org.cloudme.webgallery.image.ImageFormat;
import org.cloudme.webgallery.image.ImageFormatFactory;
import org.cloudme.webgallery.service.PhotoService;

@UrlBinding("/gallery/photo/{photoId}_{format}.{type}")
public class PhotoActionBean extends AbstractActionBean {
    private String photoId;
    private ImageFormat format;
    private ContentType type;
    @SpringBean
    private PhotoService service;
    
    @DefaultHandler
    public void show() throws IOException {
        HttpServletResponse response = getContext().getResponse();
        response.setContentType(type.toString());
        ServletOutputStream out = response.getOutputStream();
        out.write(service.getPhotoData(photoId, format, type));
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public void setFormat(String format) {
        this.format = ImageFormatFactory.getImageFormat(format);
    }

    public void setType(String type) {
        this.type = ContentTypeFactory.getContentType(type);
    }
}
