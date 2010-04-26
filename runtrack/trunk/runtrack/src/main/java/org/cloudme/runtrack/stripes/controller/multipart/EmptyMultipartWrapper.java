package org.cloudme.runtrack.stripes.controller.multipart;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.controller.FileUploadLimitExceededException;
import net.sourceforge.stripes.controller.multipart.MultipartWrapper;

public class EmptyMultipartWrapper implements MultipartWrapper {
    public void build(HttpServletRequest request, File tempDir, long maxPostSize) throws IOException,
            FileUploadLimitExceededException {
    }

    public Enumeration<String> getFileParameterNames() {
        return null;
    }

    public FileBean getFileParameterValue(String name) {
        return null;
    }

    public Enumeration<String> getParameterNames() {
        return null;
    }

    public String[] getParameterValues(String name) {
        return null;
    }
}
