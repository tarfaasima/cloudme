package org.cloudme.notepad.note;

import java.util.List;

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

    public List<Note> listByMeetingId(Id<Meeting, Long> id) {
        return listBy(filter("meetingId", id.value()), orderBy("creationDate"));
    }

    public List<Note> listAllTodos() {
        return listBy(filter("todo", true), filter("done", false), orderBy("todo"), orderBy("dueDate"));
    }

}
