package org.cloudme.notepad.meeting;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.cloudme.notepad.guice.GuiceModules;
import org.cloudme.sugar.AbstractServiceTestCase;
import org.junit.Test;

import com.google.inject.Inject;
import com.google.inject.Module;

public class MeetingServiceTest extends AbstractServiceTestCase {
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

	@Override
	protected Module[] getModules() {
		return GuiceModules.MODULES;
	}

}
