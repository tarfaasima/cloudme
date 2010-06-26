package org.cloudme.webgallery.stripes.action;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.image.ContentTypeFactory;
import org.cloudme.webgallery.image.ImageFormat;
import org.cloudme.webgallery.image.ImageFormatFactory;
import org.cloudme.webgallery.image.ImageServiceException;
import org.cloudme.webgallery.image.OverQuotaImageLoader;
import org.cloudme.webgallery.service.PhotoDataService;
import org.cloudme.webgallery.service.PhotoService;

import com.google.inject.Inject;

@UrlBinding( "/gallery/photo/{photoId}_{format}.{type}" )
public class PhotoActionBean extends AbstractActionBean {
    private Long photoId;
    private ImageFormat format;
    private ContentType type;
    @Inject
    private PhotoDataService service;
    @Inject
    private PhotoService photoService;

    @DefaultHandler
    public void show() throws IOException {
        HttpServletResponse response = getContext().getResponse();
        response.setContentType(type.toString());
        ServletOutputStream out = response.getOutputStream();
        byte[] data;
        try {
            data = service.getPhotoData(photoId, format, type);
        }
        catch (ImageServiceException e) {
            OverQuotaImageLoader loader = new OverQuotaImageLoader() {
                @Override
                protected InputStream loadResource(String fileName) {
                    return getContext().getServletContext()
                            .getResourceAsStream("/images/" + fileName);
                }
            };
            data = loader.load(format);
        }
        out.write(data);
    }

    public void setPhotoId(String photoId) {
        try {
            this.photoId = Long.valueOf(photoId);
        }
        catch (NumberFormatException e) {
            this.photoId = photoService.getRandomPhotoId();
        }
    }

    public void setFormat(String format) {
        this.format = ImageFormatFactory.getImageFormat(format);
    }

    public void setType(String type) {
        this.type = ContentTypeFactory.getContentType(type);
    }
}
