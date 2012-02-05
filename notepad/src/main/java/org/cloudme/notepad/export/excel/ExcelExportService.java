package org.cloudme.notepad.export.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import jxl.Workbook;
import jxl.write.DateFormats;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import lombok.extern.java.Log;

import org.cloudme.notepad.meeting.Meeting;
import org.cloudme.notepad.note.Note;
import org.cloudme.notepad.note.NoteService;
import org.cloudme.sugar.Id;

import com.google.inject.Inject;

@Log
public class ExcelExportService {
    private static final WritableCellFormat NORMAL = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10,
            WritableFont.NO_BOLD));
    private static final WritableCellFormat BOLD = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10,
            WritableFont.BOLD));
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    @Inject private NoteService noteService;

    public void export(Meeting meeting, OutputStream out) {
        try {
            WritableWorkbook workbook = Workbook.createWorkbook(out);
            WritableSheet sheet = workbook.createSheet("Notepad", 0);
            exportHeader(sheet);
            List<Note> notes = noteService.listByMeetingId(Id.of(meeting));
            for (int i = 0, size = notes.size(); i < size;) {
                exportNote(sheet, notes.get(i++), meeting, i);
            }
            workbook.write();
            workbook.close();
        }
        catch (IOException e) {
            log.log(Level.SEVERE, "Error while exporting meeting " + meeting.getId() + " to Excel.", e);
        }
        catch (WriteException e) {
            log.log(Level.SEVERE, "Error while exporting meeting " + meeting.getId() + " to Excel.", e);
        }
    }

    public String getContentType() {
        return "application/vnd.ms-excel";
    }

    private void exportNote(WritableSheet sheet, Note note, Meeting meeting, int row) throws WriteException {
        sheet.addCell(new Label(0, row, meeting.getTopic(), NORMAL));
        sheet.addCell(new DateTime(1, row, meeting.getDate(), new WritableCellFormat(DateFormats.DEFAULT)));
        sheet.addCell(new Label(2, row, note.getContent(), NORMAL));
        String responsible = note.getResponsible();
        if (responsible != null) {
            sheet.addCell(new Label(3, row, responsible, NORMAL));
        }
        Date dueDate = note.getDueDate();
        if (dueDate != null) {
            sheet.addCell(new DateTime(4, row, dueDate, new WritableCellFormat(DateFormats.DEFAULT)));
        }
    }

    private void exportHeader(WritableSheet sheet) throws WriteException {
        new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        int col = 0;
        sheet.addCell(new Label(col++, 0, "Topic", BOLD));
        sheet.addCell(new Label(col++, 0, "Date", BOLD));
        sheet.addCell(new Label(col++, 0, "Content", BOLD));
        sheet.addCell(new Label(col++, 0, "Responsible", BOLD));
        sheet.addCell(new Label(col++, 0, "Due Date", BOLD));
    }

    public String createFileName(Meeting meeting) {
        return meeting.getTopic() + " " + DATE_FORMAT.format(meeting.getDate()) + ".xls";
    }
}
