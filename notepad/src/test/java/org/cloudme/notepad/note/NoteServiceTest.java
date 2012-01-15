package org.cloudme.notepad.note;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import lombok.val;

import org.cloudme.notepad.guice.GuiceModules;
import org.cloudme.notepad.meeting.Meeting;
import org.cloudme.notepad.meeting.MeetingService;
import org.cloudme.sugar.AbstractServiceTestCase;
import org.cloudme.sugar.Id;
import org.junit.Test;

import com.google.inject.Inject;
import com.google.inject.Module;

public class NoteServiceTest extends AbstractServiceTestCase {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    @Inject private NoteService noteService;
    @Inject private MeetingService meetingService;

    @Test
    public void testHasNotes() throws Throwable {
        Meeting meeting = new Meeting();
        meeting.setDate(DATE_FORMAT.parse("21.09.2011"));
        meeting.setTopic("Foo Bar");
        meetingService.put(meeting);
        val meetingId = Id.of(meeting);

        assertFalse(noteService.hasNotes(meetingId));

        Note note = new Note();
        note.setContent("This is a test");
        note.setMeetingId(meeting.getId());
        noteService.put(note);

        assertTrue(noteService.hasNotes(meetingId));
    }

    @Override
    protected Module[] getModules() {
        return GuiceModules.MODULES;
    }
}
