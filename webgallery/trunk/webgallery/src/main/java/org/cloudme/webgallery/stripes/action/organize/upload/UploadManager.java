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
import org.cloudme.webgallery.image.ContentType;
import org.cloudme.webgallery.image.ContentTypeFactory;
import org.cloudme.webgallery.model.Photo;
import org.cloudme.webgallery.model.PhotoData;

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
						String fileName = new File(entry.getName()).getName();
						if (!fileName.startsWith(".")) {
							ContentType type = ContentTypeFactory.getContentTypeByFileName(fileName);
                            if (type != null) {
								String contentType = type.toString();
								long size = entry.getSize();
								byte[] data = IOUtils.toByteArray(in);
								Photo photo = createPhoto(fileName, contentType, size, data);
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
				String fileName = fileBean.getFileName();
				String contentType = fileBean.getContentType();
				long size = fileBean.getSize();
				byte[] data = IOUtils.toByteArray(fileBean.getInputStream());
				return Arrays.asList(createPhoto(fileName, contentType, size, data));
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

		private static Photo createPhoto(String fileName, String contentType, long size, byte[] data) {
			Photo photo = new Photo();
			photo.setContentType(contentType);
			photo.setFileName(fileName);
			photo.setSize(size);
			PhotoData photoData = new PhotoData();
			photoData.setDataAsArray(data);
			photo.setPhotoData(photoData);
			return photo;
		}
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
