package org.cloudme.uploader;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.google.inject.Guice;
import com.google.inject.Inject;

public class UploaderServlet extends HttpServlet {
    @Inject
    private ItemDao dao;

    @Override
    public void init() throws ServletException {
        Guice.createInjector(new UploaderModule()).injectMembers(this);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = validateId(req.getPathInfo().substring(1));
        if (id != null) {
            Item item = dao.find(id);
            resp.setContentType(item.getContentType());
            resp.setHeader("Content-Disposition", "attachment; filename=" + item.getName());
            ServletOutputStream out = resp.getOutputStream();
            IOUtils.write(item.getData(), out);
        }
    }

    private Long validateId(String idStr) {
        try {
            Long id = new Long(idStr);
            return id;
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        if (isMultipart) {
            ServletFileUpload upload = new ServletFileUpload();
            try {
                for (FileItemIterator it = upload.getItemIterator(req); it.hasNext();) {
                    FileItemStream fileItem = it.next();
                    InputStream stream = fileItem.openStream();
                    if (!fileItem.isFormField()) {
                        byte[] bytes = IOUtils.toByteArray(stream);
                        Item item = new Item();
                        item.setData(bytes);
                        item.setName(fileItem.getName());
                        item.setContentType(fileItem.getContentType());
                        dao.put(item);
                        String url = req.getRequestURL().append("/").append(item.getId()).toString();
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
