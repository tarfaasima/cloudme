package org.cloudme.mediacopy;

import java.io.File;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class MediamoveMain {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Illegal number of arguments.\n\n    mediacopy <orig> <dest>");
            System.exit(-1);
        }
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(CopyListener.class).to(SystemListener.class);
            }
        });
        OtrMove move = new OtrMove();
        injector.injectMembers(move);
        move.setOriginalsDir(args[0]);
        move.setDestDir(args[1]);
        FileLog log = new FileLog(new File(System.getProperty("user.home")
                                           + "/Library/Application Support/Mediamove/filelog.ser"));
        move.setFileLog(log);
        move.copy();
    }
}
