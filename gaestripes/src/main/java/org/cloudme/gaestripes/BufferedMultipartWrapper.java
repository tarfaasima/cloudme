package org.cloudme.gaestripes;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.controller.FileUploadLimitExceededException;
import net.sourceforge.stripes.controller.multipart.MultipartWrapper;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

/**
 * An implementation of MultipartWrapper that uses Jakarta Commons FileUpload (from apache)
 * to parse the request parts. This implementation requires that both commons-fileupload and
 * commons-io be present in the classpath.  While this implementation does introduce additional
 * dependencies, it's licensing (ASL 2.0) is significantly less restrictive than the licensing
 * for COS - the other alternative provided by Stripes. This implementation allows handling
 * uploads on the Google App Engine, as it does not rely on storing the files temporarily
 * on the local file system.
 * 
 * @author Moritz Petersen
 */
public class BufferedMultipartWrapper implements MultipartWrapper {
    /**
     * Ensure this class will not load unless Commons FileUpload is on the
     * classpath.
     */
    static {
        FileUploadException.class.getName();
    }

    private final Hashtable<String, FileBean> files = new Hashtable<String, FileBean>();
    private final Hashtable<String, String[]> parameters = new Hashtable<String, String[]>();

    /**
     * Pseudo-constructor that allows the class to perform any initialization
     * necessary.
     * 
     * @param request
     *            an HttpServletRequest that has a content-type of multipart.
     * @param tempDir
     *            a File representing the temporary directory that can be used
     *            to store file parts as they are uploaded if this is desirable
     * @param maxPostSize
     *            the size in bytes beyond which the request should not be read,
     *            and a FileUploadLimitExceeded exception should be thrown
     * @throws IOException
     *             if a problem occurs processing the request of storing
     *             temporary files
     * @throws FileUploadLimitExceededException
     *             if the POST content is longer than the maxPostSize supplied.
     */
    public void build(HttpServletRequest request, File tempDir, long maxPostSize) throws IOException, FileUploadLimitExceededException {
        try {
            String charset = request.getCharacterEncoding();
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(tempDir);
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(maxPostSize);
            Map<String, List<String>> params = new HashMap<String, List<String>>();

            for (FileItemIterator it = upload.getItemIterator(request); it.hasNext();) {
                FileItemStream item = it.next();
                // If it's a form field, add the string value to the list
                final byte[] buffer = IOUtils.toByteArray(item.openStream());
                if (item.isFormField()) {
                    List<String> values = params.get(item.getFieldName());
                    if (values == null) {
                        values = new ArrayList<String>();
                        params.put(item.getFieldName(), values);
                    }
                    values.add(charset == null ? new String(buffer) : new String(buffer, charset));
                }
                // Else store the file param
                else {
                    files.put(item.getFieldName(), new FileBean(null, item.getContentType(), item.getName() ) {
                        @Override
                        public long getSize() {
                            return buffer.length;
                        }
                        
                        @Override
                        public InputStream getInputStream() throws IOException {
                            return new ByteArrayInputStream(buffer);
                        }
                        
                        @Override
                        public void save(File toFile) throws IOException {
                            throw new UnsupportedOperationException();
                        }
                        
                        @Override
                        public void delete() throws IOException {
                            throw new UnsupportedOperationException();
                        }
                    });
                }
            }

            // Now convert them down into the usual map of String->String[]
            for (Map.Entry<String, List<String>> entry : params.entrySet()) {
                List<String> values = entry.getValue();
                parameters.put(entry.getKey(), values.toArray(new String[values.size()]));
            }
        }
        catch (FileUploadBase.SizeLimitExceededException slee) {
            throw new FileUploadLimitExceededException(maxPostSize, slee.getActualSize());
        }
        catch (FileUploadException fue) {
            IOException ioe = new IOException("Could not parse and cache file upload data.");
            ioe.initCause(fue);
            throw ioe;
        }

    }

    /**
     * Fetches the names of all non-file parameters in the request. Directly
     * analogous to the method of the same name in HttpServletRequest when the
     * request is non-multipart.
     * 
     * @return an Enumeration of all non-file parameter names in the request
     */
    public Enumeration<String> getParameterNames() {
        return parameters.keys();
    }

    /**
     * Fetches all values of a specific parameter in the request. To simulate
     * the HTTP request style, the array should be null for non-present
     * parameters, and values in the array should never be null - the empty
     * String should be used when there is value.
     * 
     * @param name
     *            the name of the request parameter
     * @return an array of non-null parameters or null
     */
    public String[] getParameterValues(String name) {
        return parameters.get(name);
    }

    /**
     * Fetches the names of all file parameters in the request. Note that these
     * are not the file names, but the names given to the form fields in which
     * the files are specified.
     * 
     * @return the names of all file parameters in the request.
     */
    public Enumeration<String> getFileParameterNames() {
        return files.keys();
    }

    /**
     * Responsible for constructing a FileBean object for the named file
     * parameter. If there is no file parameter with the specified name this
     * method should return null.
     * 
     * @param name
     *            the name of the file parameter
     * @return a FileBean object wrapping the uploaded file
     */
    public FileBean getFileParameterValue(String name) {
        return files.get(name);
    }
}
