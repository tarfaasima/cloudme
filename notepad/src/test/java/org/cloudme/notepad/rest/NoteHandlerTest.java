package org.cloudme.notepad.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.cloudme.notepad.meeting.Meeting;
import org.cloudme.notepad.meeting.MeetingModule;
import org.cloudme.notepad.meeting.MeetingService;
import org.cloudme.notepad.note.Note;
import org.cloudme.notepad.note.NoteModule;
import org.cloudme.notepad.note.NoteService;
import org.cloudme.sugar.AbstractServiceTestCase;
import org.cloudme.sugar.Id;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Inject;
import com.google.inject.Module;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.servletunit.ServletRunner;

public class NoteHandlerTest extends AbstractServiceTestCase {
    private static final File WEB_XML = new File("src/main/webapp/WEB-INF/web.xml");
    private static final Module[] MODULES = { new NoteModule(), new MeetingModule() };
    @Inject private NoteService noteService;
    @Inject private MeetingService meetingService;
    private ServletRunner sr;

    @Before
    public void setupServletRunner() throws Exception {
        sr = new ServletRunner(WEB_XML);
        // TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void testFind() throws Exception {
        assertEquals("null", sr.getResponse("http://www.example.com/api/note/find/10").getText());
        Note note = new Note();
        note.setContent("ABC");
        noteService.put(note);
        String jsonText = sr.getResponse("http://www.example.com/api/note/find/" + note.getId()).getText();
        assertTrue(jsonText, jsonText.contains("\"content\":\"ABC\""));
    }

    @Test
    public void testSave() throws Exception {
        assertEquals(0, noteService.listAll().size());
        PostMethodWebRequest req = new PostMethodWebRequest("http://www.example.com/api/note/save");
        req.setParameter("note.content", "ABC");
        req.setParameter("topic", "My Topic");
        req.setParameter("dateStr", "15.3.2012");
        sr.newClient().getResponse(req);
        assertEquals(1, noteService.listAll().size());
        Note note = noteService.findAll().iterator().next();
        assertEquals("ABC", note.getContent());
        Meeting meeting = meetingService.find(Id.of(Meeting.class, note.getMeetingId()));
        assertEquals("My Topic", meeting.getTopic());
        assertEquals(date("15.3.2012"), meeting.getDate());
    }

    @Test
    public void testDelete() throws Exception {
        assertEquals(0, noteService.listAll().size());
        Note note = new Note();
        note.setContent("ABC");
        meetingService.create(note, date("15.3.2012"), "New Topic");
        assertEquals(1, noteService.listAll().size());
        sr.getResponse(String.format("http://www.example.com/api/note/delete/%s/%s", note.getId(), note.getMeetingId()));
        assertEquals(0, noteService.listAll().size());
    }

    @Test
    public void testCheck() throws Exception {
        Note note = new Note();
        note.setContent("ABC");
        note.setResponsible("XYZ");
        meetingService.create(note, date("15.3.2012"), "New Topic");
        assertFalse(noteService.listAll().get(0).isDone());
        sr.getResponse(String.format("http://www.example.com/api/note/check/%s", note.getId()));
        assertTrue(noteService.listAll().get(0).isDone());
    }

    @Override
    protected Module[] getModules() {
        return MODULES;
    }

    private Date date(String str) throws ParseException {
        return new SimpleDateFormat("dd.MM.yyyy").parse(str);
    }

}
