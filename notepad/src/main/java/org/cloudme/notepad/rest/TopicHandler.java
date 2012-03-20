package org.cloudme.notepad.rest;

import java.util.List;

import org.cloudme.notepad.meeting.Meeting;
import org.cloudme.notepad.meeting.MeetingService;
import org.cloudme.wrestle.ActionHandler;
import org.cloudme.wrestle.annotation.Get;
import org.cloudme.wrestle.annotation.UrlMapping;

import com.google.inject.Inject;

@UrlMapping( "/api/topic" )
public class TopicHandler implements ActionHandler {
    @Inject private MeetingService meetingService;

    @Get
    public List<Meeting> list() {
        return meetingService.listAll();
    }
}