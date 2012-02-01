package org.cloudme.notepad.meeting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.cloudme.notepad.guice.GuiceModules;
import org.cloudme.notepad.note.Note;
import org.cloudme.notepad.note.NoteService;
import org.cloudme.sugar.AbstractServiceTestCase;
import org.cloudme.sugar.Id;
import org.junit.Test;

import com.google.inject.Inject;
import com.google.inject.Module;

public class MeetingServiceTest extends AbstractServiceTestCase {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
	private static final DateFormat DATE_FORMAT_WITH_TZ = new SimpleDateFormat("dd.MM.yyyy Z");
    @Inject private MeetingService meetingService;
    @Inject private NoteService noteService;

    @Test
    public void testFindAllTopics() {
        Collection<String> topics = (Collection<String>) meetingService.findAllTopics();
        assertEquals(3, topics.size());

        Meeting meeting = new Meeting();
        meeting.setTopic("A");
        meetingService.put(meeting);

        topics = (Collection<String>) meetingService.findAllTopics();
        assertEquals(1, topics.size());
        assertEquals("A", topics.iterator().next());
    }

    @Test
    public void testCreate() throws Throwable {
        Note n1 = new Note();
        n1.setContent("This is a test");
        meetingService.create(n1, DATE_FORMAT.parse("21.09.2011"), "My topic");
        Long id1 = n1.getMeetingId();

        Note n2 = new Note();
        n2.setContent("This is another test");
        meetingService.create(n2, DATE_FORMAT.parse("21.09.2011"), "My topic");
        Long id2 = n2.getMeetingId();

        assertEquals(id1, id2);
    }

    @Test
    public void testUpdate() throws Throwable {
        Note n1 = new Note();
        n1.setContent("This is a test");
        meetingService.create(n1, DATE_FORMAT.parse("21.09.2011"), "My topic");

        assertEquals(1, meetingService.listAll().size());

        Note n2 = new Note();
        n2.setContent("This is another test");
        meetingService.create(n2, DATE_FORMAT.parse("21.09.2011"), "Another topic");

        assertEquals(2, meetingService.listAll().size());

        meetingService.update(n2, DATE_FORMAT.parse("21.09.2011"), "My topic");

        assertEquals(1, meetingService.listAll().size());
    }

    @Test
    public void testRemove() throws Throwable {
        Note n1 = new Note();
        n1.setContent("This is a test");
        meetingService.create(n1, DATE_FORMAT.parse("21.09.2011"), "My topic");

        Note n2 = new Note();
        n2.setContent("This is another test");
        meetingService.create(n2, DATE_FORMAT.parse("21.09.2011"), "My topic");

        assertEquals(n1.getMeetingId(), n2.getMeetingId());
        Id<Meeting, Long> meetingId = Id.of(Meeting.class, n1.getMeetingId());
        assertNotNull(meetingService.find(meetingId));
        assertEquals(2, noteService.listByMeetingId(meetingId).size());

        meetingService.remove(meetingId, Id.of(n2));

        assertNotNull(meetingService.find(meetingId));
        assertEquals(1, noteService.listByMeetingId(meetingId).size());
        assertNull(noteService.find(Id.of(n2)));
        assertNotNull(noteService.find(Id.of(n1)));

        meetingService.remove(meetingId, Id.of(n1));

        assertNull(meetingService.find(meetingId));
        assertEquals(0, noteService.listByMeetingId(meetingId).size());
        assertNull(noteService.find(Id.of(n1)));
    }

	@Test
	public void testListAll() throws Throwable {
		Note n1 = new Note();
		n1.setContent("This is a test");
		meetingService.create(n1, DATE_FORMAT_WITH_TZ.parse("21.09.2011 +0000"), "My topic");

		Note n2 = new Note();
		n2.setContent("This is another test");
		meetingService.create(n2, DATE_FORMAT_WITH_TZ.parse("20.09.2011 +0000"), "My topic");

		Note n3 = new Note();
		n3.setContent("This is yet another test");
		meetingService.create(n3, DATE_FORMAT_WITH_TZ.parse("22.09.2011 +0000"), "All new topic");

		List<Meeting> meetings = meetingService.listAll();
		// for (Meeting meeting : meetings) {
		// System.out.println(meeting.getId() + " - " + meeting.getTopic() +
		// " - " + meeting.getDate());
		// }
		Iterator<Meeting> it = meetings.iterator();
		assertEquals(n3.getMeetingId(), it.next().getId());
		assertEquals(n1.getMeetingId(), it.next().getId());
		assertEquals(n2.getMeetingId(), it.next().getId());
		assertFalse(it.hasNext());
	}

    @Override
    protected Module[] getModules() {
        return GuiceModules.MODULES;
    }
}
