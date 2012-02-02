package org.cloudme.notepad.stripes.action.meeting;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.notepad.meeting.Meeting;
import org.cloudme.notepad.meeting.MeetingService;
import org.cloudme.notepad.note.Note;
import org.cloudme.notepad.note.NoteService;
import org.cloudme.sugar.AbstractActionBean;
import org.cloudme.sugar.Id;

import com.google.inject.Inject;

@Getter
@Setter
@UrlBinding( "/app/meeting/{$event}/{meeting.id}" )
public class MeetingActionBean extends AbstractActionBean {
    @Inject private MeetingService meetingService;
    @Inject private NoteService noteService;
    private List<Meeting> meetings;
    private List<Note> notes;
    private Meeting meeting;

    @DefaultHandler
    public Resolution list() {
        return resolve("list.jsp");
    }

    public Resolution show() {
        meeting = meetingService.find(Id.of(meeting));
        return resolve("show.jsp");
    }

    public List<Meeting> getMeetings() {
        if (meetings == null) {
            meetings = meetingService.listAll();
        }
        return meetings;
    }

    public List<Note> getNotes() {
        if (notes == null) {
            notes = noteService.listByMeetingId(Id.of(meeting));
        }
        return notes;
    }
}
