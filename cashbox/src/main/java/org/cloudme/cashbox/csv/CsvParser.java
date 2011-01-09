package org.cloudme.cashbox.csv;

public class CsvParser {
    private static final String[] EMPTY = {};
    private static final char DELIMITER = ';';
    private static final char BOUNDARY = '"';
    private static final String SPLIT_WITH_BOUNDARY = "\\s*" + BOUNDARY + DELIMITER + BOUNDARY + "\\s*";
    private static final String SPLIT_WITHOUT_BOUNDARY = "\\s*" + DELIMITER + "\\s*";

    public String[] parse(String line) {
        if (line == null) {
            return EMPTY;
        }
        line = line.trim();
        if (line.length() == 0) {
            return EMPTY;
        }
        char first = line.charAt(0);
        char last = line.charAt(line.length() - 1);
        if (first == BOUNDARY && last == BOUNDARY) {
            return line.substring(1, line.length() - 2).split(SPLIT_WITH_BOUNDARY);
        }
        else {
            return line.split(SPLIT_WITHOUT_BOUNDARY);
        }
    }
}
