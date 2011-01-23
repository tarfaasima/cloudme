package org.cloudme.mediacopy;

import java.io.File;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class MediacopyMain {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Illegal number of arguments.\n\n    mediacopy <originals> <cut> <dest>");
            System.exit(-1);
        }
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(CopyListener.class).to(SystemListener.class);
            }
        });
        OtrCopy copy = new OtrCopy();
        injector.injectMembers(copy);
        copy.setOriginalsDir(args[0]);
        copy.setCutDir(args[1]);
        copy.setDestDir(args[2]);
        FileLog log = new FileLog(new File(System.getProperty("user.home")
                + "/Library/Application Support/Mediacopy/filelog.ser"));
        copy.setFileLog(log);
        copy.copy();
    }
}
