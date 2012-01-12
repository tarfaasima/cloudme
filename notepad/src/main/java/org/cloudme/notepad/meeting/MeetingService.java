package org.cloudme.notepad.meeting;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import lombok.val;

import org.cloudme.sugar.AbstractService;

import com.google.inject.Inject;

public class MeetingService extends AbstractService<Meeting> {

    private final MeetingDao dao;

    @Inject
    public MeetingService(MeetingDao dao) {
        super(dao);
        this.dao = dao;
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

    public Meeting findOrCreate(Date date, String topic) {
        Meeting meeting = dao.findSingleByDateAndTopic(date, topic);
        if (meeting == null) {
            meeting = new Meeting();
            meeting.setDate(date);
            meeting.setTopic(topic);
            dao.put(meeting);
        }
        return meeting;
    }

}
