package org.cloudme.notepad.note;

import static org.junit.Assert.assertNotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.cloudme.notepad.guice.GuiceModules;
import org.cloudme.sugar.AbstractServiceTestCase;
import org.junit.Test;

import com.google.inject.Inject;
import com.google.inject.Module;

public class NoteServiceTest extends AbstractServiceTestCase {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    @Inject
    private NoteService noteService;

    @Test
    public void testPut() throws Throwable {
        Note note = new Note();
        note.setContent("This is a test");
        note.setResponsible("Steve");
        noteService.put(note, DATE_FORMAT.parse("21.09.2011"), "This is a topic.");
        assertNotNull(note.getId());
        assertNotNull(note.getMeetingId());
    }

    @Override
    protected Module[] getModules() {
        return GuiceModules.MODULES;
    }
}
