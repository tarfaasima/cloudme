package org.cloudme.notepad.note;

import org.cloudme.notepad.meeting.Meeting;
import org.cloudme.sugar.AbstractDao;
import org.cloudme.sugar.Id;

class NoteDao extends AbstractDao<Note> {

    public NoteDao() {
        super(Note.class);
    }

    public Iterable<Note> findByMeetingId(Id<Meeting, Long> id) {
        return findBy(filter("meetingId", id.value()));
    }

}
