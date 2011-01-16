package org.cloudme.mediacopy;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OtrFile {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yy.MM.ddHH-mm");
    private final String suffix;
    private final String channel;
    private final String title;
    private final Date date;
    private final File file;

    /**
     * Creates a new {@link OtrFile} with the given file name. The file name
     * must have the following structure:
     * 
     * <code>Das_Bourne_Ultimatum_10.12.27_22-15_zdf_105_TVOON_DE.mpg.avi</code>
     * 
     * <code>Title_With_Underscores_yy.MM.dd_HH-mm_Channel_Duration_TVOON_XX.suffix</code>
     * 
     * @param fileName
     *            Filename of the OTR file.
     */
    public OtrFile(File file) {
        this.file = file;
        final String fileName = file.getName();
        final int suffixIndex = fileName.indexOf('.', fileName.lastIndexOf('_'));
        if (suffixIndex == -1) {
            throw new IllegalStateException("File has no suffix: " + fileName);
        }
        suffix = fileName.substring(suffixIndex + 1);
        final String[] parts = fileName.substring(0, suffixIndex).split("_");
        if (parts.length < 7) {
            throw new IllegalStateException("Incorrect file name: " + fileName);
        }
        channel = parts[parts.length - 4].toUpperCase();
        try {
            String dateStr = parts[parts.length - 6] + parts[parts.length - 5];
            date = DATE_FORMAT.parse(dateStr);
        }
        catch (ParseException e) {
            throw new IllegalStateException("Unable to parse Date: " + fileName);
        }
        title = Strings.merge(parts, " ", 0, parts.length - 7);
    }

    public String getSuffix() {
        return suffix;
    }

    public String getChannel() {
        return channel;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }
}
