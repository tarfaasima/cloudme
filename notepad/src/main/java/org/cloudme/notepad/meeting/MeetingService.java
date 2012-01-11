package org.cloudme.notepad.meeting;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import lombok.val;

import org.cloudme.sugar.AbstractService;

import com.google.inject.Inject;

public class MeetingService extends AbstractService<Meeting> {

    @Inject
    public MeetingService(MeetingDao dao) {
        super(dao);
    }

    public Set<String> findAllTopics() {
        val topics = new TreeSet<String>();
        for (val meeting : findAll()) {
            topics.add(meeting.getTopic());
        }
        if (topics.isEmpty()) {
            // some sample topics
            topics.addAll(Arrays.asList("Project Status", "Team Meeting", "DB Setup"));
        }
        return topics;
    }

}
