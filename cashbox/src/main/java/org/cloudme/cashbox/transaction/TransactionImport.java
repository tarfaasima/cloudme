package org.cloudme.cashbox.transaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TransactionImport {
    private int width;
    private final Collection<String[]> lines = new ArrayList<String[]>();
    private List<String> mappings;
    private List<List<String>> entries;

    public void add(String[] items) {
        if (items.length == 0) {
            return;
        }
        width = Math.max(width, items.length);
        lines.add(items);
    }

    public List<List<String>> getEntries() {
        if (entries == null) {
            entries = new ArrayList<List<String>>();
            for (String[] line : lines) {
                List<String> entry = new ArrayList<String>();
                for (int i = 0; i < line.length; i++) {
                    entry.add(line[i]);
                }
                for (int i = line.length; i < width; i++) {
                    entry.add("");
                }
                entries.add(entry);
            }
        }
        return entries;
    }

    public void setEntries(List<List<String>> entries) {
        this.entries = entries;
    }

    public List<String> getMappings() {
        if (mappings == null) {
            mappings = new ArrayList<String>();
            for (int i = 0; i < width; i++) {
                mappings.add("");
            }
        }
        return mappings;
    }

    public void setMappings(List<String> mappings) {
        this.mappings = mappings;
    }

    public int getWidth() {
        return width;
    }
}
