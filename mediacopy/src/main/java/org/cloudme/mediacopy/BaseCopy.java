package org.cloudme.mediacopy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;

import com.google.inject.Inject;

public abstract class BaseCopy {
    private static final long BUFLEN = 1024 * 1024 * 50;
    private File destDir;
    private FileLog fileLog;
    @Inject
    private CopyListener copyListener;

    public final void copy() {
        if (!destDir.exists()) {
            fireCopyFailed(null, null, "Directory " + destDir.getAbsolutePath() + " does not exist.");
            return;
        }
        fileLog.load();
        Collection<CopyParam> params = prepareCopy();
        long size = 0;
        int count = 0;
        fireCopyInitialized();
        for (Iterator<CopyParam> it = params.iterator(); it.hasNext();) {
            CopyParam param = it.next();
            File src = param.getSrc();
            if (!fileLog.contains(src)) {
                size += src.length();
                count++;
            }
            else {
                it.remove();
            }
        }
        fireCopyPrepared(size, count);
        for (CopyParam param : params) {
            File src = param.getSrc();
            File dest = param.getDest();
            fireCopyStarted(src, dest);
            try {
                copyFile(src, dest);
                fileLog.put(src);
                fireCopySuccess(src, dest);
            }
            catch (IOException e) {
                fireCopyFailed(src, dest, e.getMessage());
            }
        }
        fireCopyCompleted();
        fileLog.save();
    }

    private void fireCopyInitialized() {
        if (copyListener == null) {
            return;
        }
        copyListener.copyInitialized();
    }

    private void fireCopyCompleted() {
        if (copyListener == null) {
            return;
        }
        copyListener.copyCompleted();
    }

    private void fireCopyFailed(File src, File dest, String message) {
        if (copyListener == null) {
            return;
        }
        copyListener.copyFailed(src, dest, message);
    }

    private void fireCopySuccess(File src, File dest) {
        if (copyListener == null) {
            return;
        }
        copyListener.copySuccess(src, dest);
    }

    private void fireCopyStarted(File src, File dest) {
        if (copyListener == null) {
            return;
        }
        copyListener.copyStarted(src, dest);
    }

    private void fireCopyPrepared(long size, int count) {
        if (copyListener == null) {
            return;
        }
        copyListener.copyPrepared(count, size);
    }

    protected abstract Collection<CopyParam> prepareCopy();

    private void copyFile(File src, File dest) throws IOException {
        if (src == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (dest == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (src.exists() == false) {
            throw new FileNotFoundException("Source '" + src + "' does not exist");
        }
        if (src.isDirectory()) {
            throw new IOException("Source '" + src + "' exists but is a directory");
        }
        if (src.getCanonicalPath().equals(dest.getCanonicalPath())) {
            throw new IOException("Source '" + src + "' and destination '" + dest + "' are the same");
        }
        if (dest.getParentFile() != null && dest.getParentFile().exists() == false) {
            if (dest.getParentFile().mkdirs() == false) {
                throw new IOException("Destination '" + dest + "' directory cannot be created");
            }
        }
        if (dest.exists() && dest.canWrite() == false) {
            throw new IOException("Destination '" + dest + "' exists but is read-only");
        }
        if (dest.exists() && dest.isDirectory()) {
            throw new IOException("Destination '" + dest + "' exists but is a directory");
        }

        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel input = null;
        FileChannel output = null;
        try {
            fis = new FileInputStream(src);
            fos = new FileOutputStream(dest);
            input = fis.getChannel();
            output = fos.getChannel();
            long size = input.size();
            long pos = 0;
            long count = 0;
            while (pos < size) {
                count = (size - pos) > BUFLEN ? BUFLEN : (size - pos);
                pos += output.transferFrom(input, pos, count);
                fireCopyProgressed(src, dest, size, pos);
            }
        }
        finally {
            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(fos);
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(fis);
        }

        if (src.length() != dest.length()) {
            throw new IOException("Failed to copy full contents from '" + src + "' to '" + dest + "'");
        }
    }

    private void fireCopyProgressed(File src, File dest, long size, long pos) {
        if (copyListener == null) {
            return;
        }
        copyListener.copyProgressed(src, dest, size, pos);
    }

    public File getDestDir() {
        return destDir;
    }

    public void setDestDir(String dir) {
        destDir = new File(dir);
    }

    public void setFileLog(FileLog fileLog) {
        this.fileLog = fileLog;
    }
}
