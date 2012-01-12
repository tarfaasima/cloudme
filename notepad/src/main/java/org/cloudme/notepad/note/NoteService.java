package org.cloudme.notepad.note;

import java.util.Date;

import org.cloudme.notepad.meeting.Meeting;
import org.cloudme.notepad.meeting.MeetingService;
import org.cloudme.sugar.AbstractService;

import com.google.inject.Inject;

public class NoteService extends AbstractService<Note> {
    @Inject
    private MeetingService meetingService;

    @Inject
    public NoteService(NoteDao dao) {
        super(dao);
    }

    @Override
    public Note put(Note t) {
        throw new UnsupportedOperationException("put(Note) not supported. Use put(Note, Date, String) instead");
    }

    public void put(Note note, Date date, String topic) {
        Meeting meeting = meetingService.findOrCreate(date, topic);
        note.setMeetingId(meeting.getId());
        super.put(note);
    }
}
