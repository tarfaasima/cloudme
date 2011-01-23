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
        System.out.printf("%d files [%s]%n", count, ByteFormat.formatMB(size));
        totalSize = size;
    }

    @Override
    public void copyStarted(File src, File dest) {
        System.out.printf("%s --> %s [%s]%n", src.getName(), dest.getName(), ByteFormat.formatMB(src.length()));
    }

    @Override
    public void copySuccess(File src, File dest) {
        long tNow = System.currentTimeMillis();
        bytesCopied += src.length();
        long spent = tNow - startTime;
        long tMax = spent * totalSize / bytesCopied;
        System.out.printf("%n%s remaining [%s of %s]%n",
                DurationFormatUtils.formatDurationHMS(tMax - spent),
                ByteFormat.formatMB(bytesCopied),
                ByteFormat.formatMB(totalSize));
    }

    @Override
    public void copyProgressed(File src, File dest, long size, long pos) {
        System.out.print('.');
    }

}
