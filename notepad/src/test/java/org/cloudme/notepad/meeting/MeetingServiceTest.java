package org.cloudme.notepad.meeting;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;

import org.cloudme.notepad.guice.GuiceModules;
import org.cloudme.notepad.note.Note;
import org.cloudme.sugar.AbstractServiceTestCase;
import org.junit.Test;

import com.google.inject.Inject;
import com.google.inject.Module;

public class MeetingServiceTest extends AbstractServiceTestCase {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    @Inject
	private MeetingService meetingService;

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

	@Override
	protected Module[] getModules() {
		return GuiceModules.MODULES;
	}
}
