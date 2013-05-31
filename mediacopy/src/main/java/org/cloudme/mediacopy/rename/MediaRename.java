package org.cloudme.mediacopy.rename;

import java.io.File;

public class MediaRename {
    public static void main(String[] args) {
        rename(args[0]);
    }

    private static void rename(String baseDir) {
        rename(new File(baseDir));
    }

    private static void rename(File file) {
        if (file.isFile()) {
            String name = file.getName();
            if (name.startsWith(".")) {
                System.out.println("Deleted: " + file);
                file.delete();
            }
            else {
                MediaFileName fileName = MediaFileName.create(name);
                if (fileName != null) {
                    File newFile = new File(file.getParent(), fileName.toNewName());
                    System.out.print("Renaming " + file + " to " + newFile);
                    if (file.renameTo(newFile)) {
                        System.out.println(" - success");
                    }
                    else {
                        System.out.println(" - failed");
                    }
                }
            }
        }
        else {
            File[] files = file.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    rename(files[i]);
                }
            }
        }
    }
}
