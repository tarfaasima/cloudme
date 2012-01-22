package org.cloudme.notepad.note;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import lombok.SneakyThrows;
import lombok.val;

import org.cloudme.notepad.guice.GuiceModules;
import org.cloudme.notepad.meeting.Meeting;
import org.cloudme.notepad.meeting.MeetingService;
import org.cloudme.sugar.AbstractServiceTestCase;
import org.cloudme.sugar.Id;
import org.junit.Test;

import com.google.inject.Inject;
import com.google.inject.Module;

public class NoteDaoTest extends AbstractServiceTestCase {
    @Inject private MeetingService meetingService;
    @Inject private NoteDao noteDao;

    @Test
    public void testListByMeetingId() {
        val date = new Date();
        String c1 = "Test Content";
        String c2 = "Another test content";
        String c3 = "Zoo content";
        Note note = createNote(date, c1);
        createNote(date, c2);
        createNote(date, c3);

        List<Note> notes = noteDao.listByMeetingId(Id.of(Meeting.class, note.getMeetingId()));

        assertEquals(3, notes.size());
        assertEquals(c3, notes.get(0).getContent());
        assertEquals(c2, notes.get(1).getContent());
        assertEquals(c1, notes.get(2).getContent());
    }

    @SneakyThrows
    private Note createNote(final java.util.Date date, String content) {
        Note note1 = new Note();
        note1.setContent(content);
        meetingService.create(note1, date, "Test Topic");
        Thread.sleep(50);
        return note1;
    }

    @Override
    protected Module[] getModules() {
        return GuiceModules.MODULES;
    }

}
