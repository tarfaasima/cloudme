package org.cloudme.notepad.note;

import java.util.List;

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

    public boolean hasNotes(Id<Meeting, Long> id) {
        return dao.findByMeetingId(id).iterator().hasNext();
    }

    public List<Note> listByMeetingId(Id<Meeting, Long> id) {
        return dao.listByMeetingId(id);
    }

    public void toggleDone(Id<Note, Long> id) {
        Note note = dao.find(id);
        note.setDone(!note.isDone());
        dao.put(note);
    }

    public List<Note> listAllTodos() {
        return dao.listAllTodos();
    }
}
