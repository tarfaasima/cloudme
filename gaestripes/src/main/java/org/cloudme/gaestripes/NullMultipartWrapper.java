package org.cloudme.gaestripes;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.controller.FileUploadLimitExceededException;
import net.sourceforge.stripes.controller.multipart.MultipartWrapper;

public class NullMultipartWrapper implements MultipartWrapper {
    @Override
    public void build(HttpServletRequest request, File tempDir, long maxPostSize) throws IOException,
            FileUploadLimitExceededException {
    }

    @Override
    public Enumeration<String> getFileParameterNames() {
        return null;
    }

    @Override
    public FileBean getFileParameterValue(String name) {
        return null;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return null;
    }

    @Override
    public String[] getParameterValues(String name) {
        return null;
    }
}