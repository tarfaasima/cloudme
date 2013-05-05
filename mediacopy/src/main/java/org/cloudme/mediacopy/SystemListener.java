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
        System.out.printf("%d files [%s]%n", count, (size >> 20) + "MB");
        totalSize = size;
    }

    @Override
    public void copyStarted(File src, File dest) {
        System.out.printf("%s --> %s [%s]%n", src.getName(), dest.getName(), (src.length() >> 20) + "MB");
    }

    @Override
    public void copySuccess(File src, File dest) {
        long tNow = System.currentTimeMillis();
        bytesCopied += dest.length();
        long spent = tNow - startTime;
        long tMax = spent * totalSize / bytesCopied;
        System.out.printf("%n%s remaining [%s of %s]%n",
                          DurationFormatUtils.formatDurationHMS(tMax - spent),
                          (bytesCopied >> 20) + "MB",
                          (totalSize >> 20) + "MB");
    }

    @Override
    public void copyProgressed(File src, File dest, long size, long pos) {
        System.out.print('.');
    }

}
