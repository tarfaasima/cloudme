package org.cloudme.webgallery.stripes.action.organize.upload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.sourceforge.stripes.action.FileBean;

import org.apache.commons.io.IOUtils;
import org.cloudme.webgallery.Photo;
import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.image.ContentTypeFactory;

public class UploadManager {
    private enum UploadHandler {
        ZIP("application/zip") {
            @Override
            public Collection<Photo> process(FileBean fileBean) throws IOException {
                Collection<Photo> photos = new ArrayList<Photo>();
                ZipInputStream in = null;
                try {
                    in = new ZipInputStream(fileBean.getInputStream());
                    ZipEntry entry;
                    while ((entry = in.getNextEntry()) != null) {
                        String name = new File(entry.getName()).getName();
                        if (!name.startsWith(".")) {
                            ContentType type = ContentTypeFactory.getContentTypeByFileName(name);
                            if (type != null) {
                                Photo photo = new Photo();
                                photo.setContentType(type.toString());
                                photo.setDataAsArray(IOUtils.toByteArray(in));
                                photo.setFileName(name);
                                photo.setSize(entry.getSize());
                                photos.add(photo);
                            }
                        }
                    }
                }
                finally {
                    if (in != null) {
                        in.close();
                    }
                }
                return photos;
            }
        },
        IMAGE("image/jpeg", "image/png") {
            @Override
            public Collection<Photo> process(FileBean fileBean) throws IOException {
                final Photo photo = new Photo();
                photo.setContentType(fileBean.getContentType());
                photo.setDataAsArray(IOUtils.toByteArray(fileBean.getInputStream()));
                photo.setFileName(fileBean.getFileName());
                photo.setSize(fileBean.getSize());
                return Arrays.asList(photo);
            }
        };

        private final String[] contentTypes;

        private UploadHandler(String... contentTypes) {
            this.contentTypes = contentTypes;
        }

        public String[] getContentTypes() {
            return contentTypes;
        }

        public abstract Collection<Photo> process(FileBean fileBean) throws IOException;
    }

    private Map<String, UploadHandler> uploadHandlerMap;

    public Collection<Photo> upload(FileBean fileBean) throws IOException {
        if (uploadHandlerMap == null) {
            uploadHandlerMap = new HashMap<String, UploadHandler>();
            for (UploadHandler uploadHandler : UploadHandler.values()) {
                for (String contentType : uploadHandler.getContentTypes()) {
                    uploadHandlerMap.put(contentType, uploadHandler);
                }
            }
        }
        return uploadHandlerMap.get(fileBean.getContentType()).process(fileBean);
    }
}
