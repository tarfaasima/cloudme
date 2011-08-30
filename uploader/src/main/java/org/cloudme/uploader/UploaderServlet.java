package org.cloudme.uploader;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadBase.FileUploadIOException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.cloudme.uploader.template.Template;

import com.google.inject.Guice;
import com.google.inject.Inject;

public class UploaderServlet extends HttpServlet {
    private static final String ENCODING = System.getProperty("file.encoding");
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
            writeData(resp, item.getData(), item.getContentType(), path.getFileNameOr(item.getName(), ENCODING));
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
                    if (!fileItem.isFormField()) {
						Item item = createItem(req, fileItem);
						dao.put(item);
                        String url = createUploadUrl(req, item);
                        writeTemplate(resp, "url", url);
                    }
                }
            }
            catch (FileUploadIOException e) {
                LOG.log(Level.WARNING, "Error while uploading.", e);
                writeTemplate(resp,
                        "error",
                        "Error while uploading. Please make sure that the file size does not exceed 1MB.");
            }
            catch (Throwable t) {
                LOG.log(Level.WARNING, "Unexpected error while uploading.", t);
                writeTemplate(resp, "error", "An unexpected error occured during file upload.");
            }
        }
    }

	private Item createItem(ServletRequest req, FileItemStream fileItem) throws IOException {
		byte[] bytes;
		bytes = IOUtils.toByteArray(fileItem.openStream());
		Item item = new Item();
		item.setUploadedBy(req.getLocalAddr());
		item.setUploadedAt(new Date());
		item.setData(bytes);
		item.setName(fileItem.getName());
		item.setContentType(fileItem.getContentType());
		return item;
	}

	private void writeData(HttpServletResponse resp, byte[] data, String contentType, String fileName)
            throws IOException {
        resp.setCharacterEncoding(ENCODING);
        resp.setContentLength(data.length);
        resp.setContentType(contentType);
        resp.setHeader("Content-Disposition", "filename=\"" + fileName + "\"");
        ServletOutputStream out = resp.getOutputStream();
        IOUtils.write(data, out);
    }

    private void writeTemplate(HttpServletResponse resp, String var, String value) throws IOException {
        resp.setContentType("text/html");
        Template template = new Template(IOUtils.toString(getClass().getResourceAsStream("upload.html")));
        template.replace(var, value);
        resp.getWriter().print(template.toString());
    }

    private String createUploadUrl(HttpServletRequest req, Item item) throws UnsupportedEncodingException {
        return req.getRequestURL().append("/").append(item.getId()).append("/")
                .append(URLEncoder.encode(item.getName(), ENCODING)).toString();
    }
}
