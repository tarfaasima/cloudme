package org.cloudme.notepad.export;

import org.cloudme.notepad.export.excel.ExcelExportService;

import com.google.inject.AbstractModule;

public class ExportModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ExcelExportService.class);
    }
}
