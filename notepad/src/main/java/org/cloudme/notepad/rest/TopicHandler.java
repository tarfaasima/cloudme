package org.cloudme.notepad.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloudme.notepad.meeting.Meeting;
import org.cloudme.notepad.meeting.MeetingGroup;
import org.cloudme.notepad.meeting.MeetingService;
import org.cloudme.notepad.note.NoteService;
import org.cloudme.sugar.Id;
import org.cloudme.wrestle.ActionHandler;
import org.cloudme.wrestle.annotation.Get;
import org.cloudme.wrestle.annotation.UrlMapping;

import com.google.inject.Inject;

@UrlMapping( "/topic" )
public class TopicHandler implements ActionHandler {
    @Inject private MeetingService meetingService;
    @Inject private NoteService noteService;

    @Get
    public List<MeetingGroup> list() {
        List<MeetingGroup> meetingGroups = meetingService.getMeetingGroups();
        System.out.println("meetingGroups = " + meetingGroups);
        return meetingGroups;
    }

    @Get
    public Iterable<String> distinct() {
        return meetingService.findAllTopics();
    }

    @Get
    public Map<String, Object> find(Long id) {
        Map<String, Object> dto = new HashMap<String, Object>();
        Id<Meeting, Long> meetingId = Id.of(Meeting.class, id);
        dto.put("meeting", meetingService.find(meetingId));
        dto.put("notes", noteService.listByMeetingId(meetingId));
        return dto;
    }
}