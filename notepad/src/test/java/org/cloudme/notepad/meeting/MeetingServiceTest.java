package org.cloudme.notepad.meeting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.cloudme.notepad.guice.GuiceModules;
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
		Set<String> topics = meetingService.findAllTopics();
		assertEquals(3, topics.size());

		Meeting meeting = new Meeting();
		meeting.setTopic("A");
		meetingService.put(meeting);

		topics = meetingService.findAllTopics();
		assertEquals(1, topics.size());
		assertEquals("A", topics.iterator().next());
	}

    @Test
    public void testFindOrCreate() throws Throwable {
        Date date = DATE_FORMAT.parse("21.09.2011");
        Meeting meeting = meetingService.findOrCreate(date, "DB Configuration Status");
        assertNotNull(meeting);
        Meeting otherMeeting = meetingService.findOrCreate(date, "DB Configuration Status");
        assertEquals(meeting.getId(), otherMeeting.getId());
    }

	@Override
	protected Module[] getModules() {
		return GuiceModules.MODULES;
	}
}
