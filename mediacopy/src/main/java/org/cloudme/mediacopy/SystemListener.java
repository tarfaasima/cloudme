package org.cloudme.mediacopy;

import java.io.File;

import org.apache.commons.lang.time.DurationFormatUtils;

public class SystemListener implements CopyListener {
    private long startTime;
    private long totalSize;
    private long bytesCopied;

    @Override
    public void copyCompleted() {
        System.out.printf("Completed in %s%n",
                DurationFormatUtils.formatDurationHMS(System.currentTimeMillis() - startTime));
    }

    @Override
    public void copyFailed(File src, File dest, String message) {
        System.out.printf("Failed: %s%n", message);
    }

    @Override
    public void copyInitialized() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void copyPrepared(int count, long size) {
        System.out.printf("%d files [%d byte]%n", count, size);
        totalSize = size;
    }

    @Override
    public void copyStarted(File src, File dest) {
        System.out.printf("%s --> %s [%d byte]%n", src.getName(), dest.getName(), src.length());
    }

    @Override
    public void copySuccess(File src, File dest) {
        long tNow = System.currentTimeMillis();
        bytesCopied += src.length();
        long spent = tNow - startTime;
        long tMax = spent * totalSize / bytesCopied;
        System.out.printf("%n%s remaining [%d of %d byte]%n",
                DurationFormatUtils.formatDurationHMS(tMax - spent),
                bytesCopied,
                totalSize);
    }

    @Override
    public void copyProgressed(File src, File dest, long size, long pos) {
        System.out.print('.');
    }

}
