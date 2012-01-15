package org.cloudme.notepad.note;

import org.cloudme.notepad.meeting.Meeting;
import org.cloudme.sugar.AbstractService;
import org.cloudme.sugar.Id;

import com.google.inject.Inject;

public class NoteService extends AbstractService<Note> {
    private final NoteDao dao;

    @Inject
    public NoteService(NoteDao dao) {
        super(dao);
        this.dao = dao;
    }

    public boolean hasNotes(Id<Meeting, Long> meetingId) {
        Iterable<Note> notes = dao.findByMeetingId(meetingId);
        return notes.iterator().hasNext();
    }
}
