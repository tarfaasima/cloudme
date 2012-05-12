package org.cloudme.notepad.export.excel;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.cloudme.notepad.guice.GuiceModules;
import org.cloudme.notepad.meeting.Meeting;
import org.cloudme.notepad.meeting.MeetingService;
import org.cloudme.notepad.note.Note;
import org.cloudme.notepad.note.NoteService;
import org.cloudme.sugar.AbstractServiceTestCase;
import org.cloudme.sugar.Id;
import org.junit.Test;

import com.google.inject.Inject;
import com.google.inject.Module;

public class ExcelExportServiceTest extends AbstractServiceTestCase {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy Z");
    @Inject ExcelExportService excelExportService;
    @Inject MeetingService meetingService;
    @Inject NoteService noteService;

    @Test
    public void testExport() throws Throwable {
        Note n1 = new Note();
        n1.setContent("Update status report\n\nRemind all team members before!");
        n1.setResponsible("Tom");
        n1.setDueDate(DATE_FORMAT.parse("14.3.2012 +0000"));
        meetingService.create(n1, DATE_FORMAT.parse("10.3.2012 +0000"), "Team meeting");

        Note n2 = new Note();
        n2.setContent("All tasks in schedule");
        meetingService.create(n2, DATE_FORMAT.parse("10.3.2012 +0000"), "Team meeting");

        Note n3 = new Note();
        n3.setContent("Present new authorization concept");
        n3.setResponsible("John");
        n3.setDueDate(DATE_FORMAT.parse("21.3.2012 +0000"));
        meetingService.create(n3, DATE_FORMAT.parse("11.3.2012 +0000"), "Authorization and Workflow");

        // FileOutputStream out = new
        // FileOutputStream("ExcelExportServiceTest_testExport.xls");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        excelExportService.export(meetingService.find(Id.of(Meeting.class, n1.getMeetingId())), out);
        // out.flush();
        // out.close();
        // byte[] actual = out.toByteArray();
        // byte[] expected =
        // toByteArray("ExcelExportServiceTest_testExport.xls");
        // assertEqualsBytes(expected, actual);
    }

    @Test
    public void testCreateFileName() throws Throwable {
        Note n1 = new Note();
        n1.setContent("Update status report\n\nRemind all team members before!");
        n1.setResponsible("Tom");
        n1.setDueDate(DATE_FORMAT.parse("14.3.2012 +0000"));
        meetingService.create(n1, DATE_FORMAT.parse("10.3.2012 +0000"), "Team meeting");

        Meeting meeting = meetingService.find(Id.of(Meeting.class, n1.getMeetingId()));
        assertEquals("Team_meeting-20120310.xls", excelExportService.createFileName(meeting));
    }

    @SuppressWarnings( "unused" )
    private static void assertEqualsBytes(byte[] expected, byte[] actual) {
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @SuppressWarnings( "unused" )
    private byte[] toByteArray(String name) throws Throwable {
        InputStream in = getClass().getResourceAsStream(name);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = in.read(bytes)) != -1) {
            out.write(bytes, 0, len);
        }
        in.close();
        return out.toByteArray();
    }

    @Override
    protected Module[] getModules() {
        return GuiceModules.MODULES;
    }

}
