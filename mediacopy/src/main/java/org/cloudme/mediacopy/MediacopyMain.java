package org.cloudme.mediacopy;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class MediacopyMain {
    /**
     * @param args
     */
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("o", "otr", true, "Directory containing videos from the Online TV Recorder website");
        options.addOption("c", "otrcut", true, "Directory containing cut versions from OTR videos");
        CommandLineParser parser = new BasicParser();
        try {
            parser.parse(options, args);
        }
        catch (ParseException e) {
        }
    }
}
