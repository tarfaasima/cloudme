package org.cloudme.mediacopy.gui;

import java.io.File;

import org.cloudme.mediacopy.CopyListener;

public class GuiListener implements CopyListener {
    @Override
    public void copyCompleted() {
        // TODO Auto-generated method stub

    }

    @Override
    public void copyFailed(File src, File dest, String message) {
        // TODO Auto-generated method stub

    }

    @Override
    public void copyInitialized() {
        // TODO Auto-generated method stub

    }

    @Override
    public void copyPrepared(int count, long size) {
        // TODO Auto-generated method stub

    }

    @Override
    public void copyStarted(File src, File dest) {
        // TODO Auto-generated method stub

    }

    @Override
    public void copySuccess(File src, File dest) {
        // TODO Auto-generated method stub

    }

    @Override
    public void copyProgressed(File src, File dest, long size, long pos) {
        // TODO Auto-generated method stub

    }

}
