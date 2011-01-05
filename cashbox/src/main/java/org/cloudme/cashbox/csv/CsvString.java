package org.cloudme.cashbox.csv;

public class CsvString {
    private String[] items;
    private static final char DELIMITER = ';';
    private static final char BOUNDARY = '"';
    private static final String SPLIT_WITH_BOUNDARY = "\\s*" + BOUNDARY + DELIMITER + BOUNDARY + "\\s*";
    private static final String SPLIT_WITHOUT_BOUNDARY = "\\s*" + DELIMITER + "\\s*";

    public void parse(String line) {
        if (line == null) {
            items = null;
            return;
        }
        line = line.trim();
        if (line.length() == 0) {
            items = null;
            return;
        }
        char first = line.charAt(0);
        char last = line.charAt(line.length() - 1);
        if (first == BOUNDARY && last == BOUNDARY) {
            items = line.substring(1, line.length() - 2).split(SPLIT_WITH_BOUNDARY);
        }
        else {
            items = line.split(SPLIT_WITHOUT_BOUNDARY);
        }
    }

    public int size() {
        return items == null ? 0 : items.length;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public String get(int index) {
        return items[index];
    }
}
