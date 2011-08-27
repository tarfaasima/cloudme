package org.cloudme.uploader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadBase.FileUploadIOException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.google.inject.Guice;
import com.google.inject.Inject;

public class UploaderServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(UploaderServlet.class.getName());
    private static final int MAX_FILE_SIZE = 1024 * 1024;
    @Inject
    private ItemDao dao;

    @Override
    public void init() throws ServletException {
        Guice.createInjector(new UploaderModule()).injectMembers(this);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestPath path = new RequestPath(req.getPathInfo());
        if (path.isValid()) {
            Item item = dao.find(path.getId());
            resp.setContentType(item.getContentType());
            resp.setHeader("Content-Length", String.valueOf(item.getData().length));
            resp.setHeader("Content-Disposition",
                    "filename=\"" + URLDecoder.decode(path.getFileNameOr(item.getName()), "UTF-8") + "\"");
            ServletOutputStream out = resp.getOutputStream();
            IOUtils.write(item.getData(), out);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        if (isMultipart) {
            ServletFileUpload upload = new ServletFileUpload();
            upload.setFileSizeMax(MAX_FILE_SIZE);
            try {
                for (FileItemIterator it = upload.getItemIterator(req); it.hasNext();) {
                    FileItemStream fileItem = it.next();
                    InputStream stream = fileItem.openStream();
                    if (!fileItem.isFormField()) {
                        byte[] bytes;
                        try {
                            bytes = IOUtils.toByteArray(stream);
                        }
                        catch (FileUploadIOException e) {
                            LOG.warning("Error while uploading " + fileItem.getName());
                            throw e;
                        }
                        Item item = new Item();
                        item.setData(bytes);
                        item.setName(fileItem.getName());
                        item.setContentType(fileItem.getContentType());
                        dao.put(item);
                        String url = req.getRequestURL().append("/").append(item.getId()).append("/")
                                .append(URLEncoder.encode(item.getName(), "UTF-8")).toString();
                        resp.getWriter().print(url);
                    }
                }
            }
            catch (FileUploadException e) {
                throw new ServletException(e);
            }
        }
    }
}
