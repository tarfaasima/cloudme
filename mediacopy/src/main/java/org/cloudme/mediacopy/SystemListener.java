package org.cloudme.mediacopy;

import java.io.File;

public class SystemListener implements CopyListener {
    private long startTime;
    private long totalSize;
    private long bytesCopied;

    @Override
    public void copyCompleted() {
        System.out.printf("Completed in %d sec.%n", (System.currentTimeMillis() - startTime) / 1000);
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
        System.out.printf("Copy %d files (%d byte)%n", count, size);
        totalSize = size;
    }

    @Override
    public void copyStarted(File src, File dest) {
        System.out.printf("Copy %s --> %s (%d byte) ", src.getName(), dest.getName(), src.length());
    }

    @Override
    public void copySuccess(File src, File dest) {
        long now = System.currentTimeMillis();
        bytesCopied += src.length();
        float percentage = bytesCopied / (float) totalSize;
        long remaining = (long) ((now - startTime) / percentage / 1000.0f);
        System.out.printf("Successful (%d / %d byte copied, %d sec remaining)%n", bytesCopied, totalSize, remaining);
    }

    @Override
    public void copyProgressed(File src, File dest, long size, long pos) {
        System.out.print('.');
    }

}
