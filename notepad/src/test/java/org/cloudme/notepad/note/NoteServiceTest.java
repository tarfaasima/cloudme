package org.cloudme.notepad.note;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

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

    @Test
    public void testListByMeetingId() throws Throwable {
        newNote("This is a test", null, null, "21.09.2011", "Test Meeting");
        Note note = newNote("This is not a test", null, null, "22.09.2011", "Taste Meeting");

        Iterator<Note> notes = noteService.listByMeetingId(Id.of(Meeting.class, note.getMeetingId())).iterator();

        assertTrue(notes.hasNext());
        assertEquals("This is not a test", notes.next().getContent());
        assertFalse(notes.hasNext());
    }

    private Note newNote(String content, String dueDate, String responsible, String meetingDate, String meetingTopic)
            throws ParseException {
        Note note = new Note();
        note.setContent(content);
        if (dueDate != null) {
            note.setDueDate(DATE_FORMAT.parse(dueDate));
        }
        note.setResponsible(responsible);
        meetingService.create(note, DATE_FORMAT.parse(meetingDate), meetingTopic);
        return note;
    }

    @Test
    public void testToggleDone() throws Throwable {
        Note note1 = newNote("This is a test", null, null, "21.09.2011", "Test Meeting");
        Note note2 = newNote("This is another test", null, null, "22.09.2011", "Taste Meeting");

        noteService.toggleDone(Id.of(note2));

        assertFalse(noteService.find(Id.of(note1)).isDone());
        assertTrue(noteService.find(Id.of(note2)).isDone());
    }

    @Test
    public void listTodos() throws Throwable {
        newNote("No To-Do", null, null, "21.09.2011", "Test Meeting");
        newNote("To-Do 1", "23.09.2011", null, "21.09.2011", "Test Meeting");
        newNote("To-Do 2", "24.09.2011", "Thomas", "21.09.2011", "Test Meeting");
        newNote("To-Do 3", null, "Thomas", "21.09.2011", "Test Meeting");

        List<Note> todos = noteService.listAllTodos();
        assertEquals(3, todos.size());
    }

    @Override
    protected Module[] getModules() {
        return GuiceModules.MODULES;
    }
}
