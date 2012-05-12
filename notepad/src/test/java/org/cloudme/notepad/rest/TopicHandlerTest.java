package org.cloudme.notepad.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.SneakyThrows;

import org.cloudme.notepad.meeting.MeetingModule;
import org.cloudme.notepad.meeting.MeetingService;
import org.cloudme.notepad.note.Note;
import org.cloudme.notepad.note.NoteModule;
import org.cloudme.sugar.AbstractServiceTestCase;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Inject;
import com.google.inject.Module;
import com.meterware.servletunit.ServletRunner;

public class TopicHandlerTest extends AbstractServiceTestCase {
    private static final File WEB_XML = new File("src/main/webapp/WEB-INF/web.xml");
    private static final Module[] MODULES = { new NoteModule(), new MeetingModule() };
    @Inject private MeetingService meetingService;
    private ServletRunner sr;

    @Before
    public void setupServletRunner() throws Exception {
        sr = new ServletRunner(WEB_XML);
    }

    @Test
    public void testList() throws Exception {
        createNote("ABC", "15.3.2012", "Topic 1");
        createNote("DEF", "15.3.2012", "Topic 1");
        createNote("GHI", "16.3.2012", "Topic 2");
        createNote("JKL", "16.3.2012", "Topic 3");
        String json = sr.getResponse("http://www.example.com/api/topic/list").getText();
        assertTrue(json, json.contains("\"topic\":\"Topic 1\""));
        assertTrue(json, json.contains("\"topic\":\"Topic 2\""));
        assertTrue(json, json.contains("\"topic\":\"Topic 3\""));
    }

    @Test
    public void testDistinct() throws Exception {
        createNote("ABC", "15.3.2012", "Topic 1");
        createNote("DEF", "15.3.2012", "Topic 2");
        createNote("GHI", "16.3.2012", "Topic 3");
        createNote("JKL", "16.3.2012", "Topic 1");
        String json = sr.getResponse("http://www.example.com/api/topic/distinct").getText();
        assertEquals("[\"Topic 1\",\"Topic 2\",\"Topic 3\"]", json);
    }

    @Test
    public void testFind() throws Exception {
        Long meetingId = createNote("ABC", "15.3.2012", "Topic 1");
        createNote("DEF", "15.3.2012", "Topic 1");
        createNote("GHI", "16.3.2012", "Topic 2");
        String json = sr.getResponse(String.format("http://www.example.com/api/topic/find/%s", meetingId)).getText();
        assertTrue(json, json.contains("\"topic\":\"Topic 1\""));
        assertTrue(json, json.contains("\"content\":\"ABC\""));
        assertTrue(json, json.contains("\"content\":\"DEF\""));
        assertFalse(json, json.contains("\"topic\":\"Topic 2\""));
        assertFalse(json, json.contains("\"content\":\"GHI\""));
    }

    private Long createNote(String content, String date, String topic) {
        Note note = new Note();
        note.setContent(content);
        meetingService.create(note, date(date), topic);
        return note.getMeetingId();
    }

    @SneakyThrows
    private Date date(String string) {
        return new SimpleDateFormat("dd.MM.yyyy").parse(string);
    }

    @Override
    protected Module[] getModules() {
        return MODULES;
    };

}
