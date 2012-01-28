package org.cloudme.notepad.stripes.action.meeting;

import java.util.List;

import lombok.Setter;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.notepad.meeting.Meeting;
import org.cloudme.notepad.meeting.MeetingService;
import org.cloudme.sugar.AbstractActionBean;

import com.google.inject.Inject;

@UrlBinding( "/app/meeting" )
public class MeetingActionBean extends AbstractActionBean {
    @Inject private MeetingService meetingService;
    @Setter private List<Meeting> meetings;

    @DefaultHandler
    public Resolution list() {
        return resolve("meeting.jsp");
    }

    public List<Meeting> getMeetings() {
        if (meetings == null) {
            meetings = meetingService.listAll();
        }
        return meetings;
    }
}
