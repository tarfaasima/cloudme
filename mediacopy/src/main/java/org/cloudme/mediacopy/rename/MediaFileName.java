package org.cloudme.mediacopy.rename;


class MediaFileName {
    private final String newName;

    public MediaFileName(String newName) {
        this.newName = newName;
    }

    public static MediaFileName create(String name) {
        int openIndex = name.indexOf('(');
        if (openIndex == -1) {
            return null;
        }
        int commaIndex = name.indexOf(',', openIndex);
        if (commaIndex == -1) {
            return null;
        }
        int closeIndex = name.indexOf(',', commaIndex + 1);
        if (closeIndex == -1) {
            closeIndex = name.indexOf(')', commaIndex + 1);
            if (closeIndex == -1) {
                return null;
            }
        }
        String title = name.substring(0, openIndex + 1);
        String channel = name.substring(openIndex + 1, commaIndex);
        String date = name.substring(commaIndex + 2, closeIndex);
        String suffix = name.substring(closeIndex);
        return new MediaFileName(title + date + ", " + channel + suffix);
    }

    public String toNewName() {
        return newName;
    }
}