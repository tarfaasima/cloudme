package org.cloudme.notepad.rest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.cloudme.notepad.meeting.MeetingModule;
import org.cloudme.notepad.note.Note;
import org.cloudme.notepad.note.NoteModule;
import org.cloudme.notepad.note.NoteService;
import org.cloudme.sugar.AbstractServiceTestCase;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Inject;
import com.google.inject.Module;
import com.meterware.servletunit.ServletRunner;

public class TaskHandlerTest extends AbstractServiceTestCase {
    private static final File WEB_XML = new File("src/main/webapp/WEB-INF/web.xml");
    private static final Module[] MODULES = { new NoteModule(), new MeetingModule() };
    @Inject private NoteService noteService;
    private ServletRunner sr;

    @Before
    public void setupServletRunner() throws Exception {
        sr = new ServletRunner(WEB_XML);
    }

    @Test
    public void testList() throws Exception {
        createNote("ABC", null, false);
        createNote("DEF", "Max", false);
        createNote("GHI", "Moritz", true);
        String json = sr.getResponse("http://www.example.com/api/task/list").getText();
        assertTrue(json, json.contains("\"content\":\"DEF\""));
        assertFalse(json, json.contains("\"content\":\"ABC\""));
        assertFalse(json, json.contains("\"content\":\"GHI\""));
    }

    private void createNote(String content, String responsible, boolean done) {
        Note note = new Note();
        note.setContent(content);
        note.setResponsible(responsible);
        note.setDone(done);
        noteService.put(note);
    }

    @Override
    protected Module[] getModules() {
        return MODULES;
    }

}
