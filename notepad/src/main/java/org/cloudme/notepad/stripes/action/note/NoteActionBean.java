package org.cloudme.notepad.stripes.action.note;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import org.cloudme.notepad.date.DateService;
import org.cloudme.notepad.meeting.Meeting;
import org.cloudme.notepad.meeting.MeetingService;
import org.cloudme.notepad.note.Note;
import org.cloudme.notepad.note.NoteService;
import org.cloudme.sugar.AbstractActionBean;
import org.cloudme.sugar.Id;

import com.google.inject.Inject;

@Getter
@Setter
@UrlBinding( "/app/note/{$event}/{note.meetingId}/{note.id}" )
public class NoteActionBean extends AbstractActionBean {
    @Inject private MeetingService meetingService;
    @Inject private NoteService noteService;
    @Inject private DateService dateService;
    private Iterable<String> topics;
    private List<Note> notes;
    private String dueDate;
    @ValidateNestedProperties( { @Validate( field = "content", required = true ) } ) private Note note;
    @Validate( required = true ) private Date date;
    @Validate( required = true ) private String topic;

    @DefaultHandler
    @DontValidate
    public Resolution create() {
        if (note == null || note.getMeetingId() == null) {
            date = Calendar.getInstance(getContext().getLocale()).getTime();
        }
        else {
            loadDateAndTopic(Id.of(Meeting.class, note.getMeetingId()));
        }
        return resolve("note.jsp");
    }

    private void loadDateAndTopic(Id<Meeting, Long> id) {
        val meeting = meetingService.find(id);
        if (meeting != null) {
            date = meeting.getDate();
            topic = meeting.getTopic();
        }
    }

    @DontValidate
    public Resolution edit() {
        if (note == null || note.getId() == null) {
            return create();
        }
        note = noteService.find(note.getId());
        return create();
    }

    public Resolution save() {
        note.setDueDate(dateService.convert(dueDate, date));
        RequestParameter[] params = new RequestParameter[] { param("note.id", note.getId()),
                param("note.meetingId", note.getMeetingId()) };
        if (note.isManaged()) {
            meetingService.update(note, date, topic);
            return redirectSelf("edit", params);
        }
        else {
            meetingService.create(note, date, topic);
            return redirectSelf("create", params);
        }
    }

    @DontValidate
    public Resolution delete() {
        noteService.delete(Id.of(note));
        return redirectSelf("create");
    }

    public synchronized Iterable<String> getTopics() {
        if (topics == null) {
            topics = meetingService.findAllTopics();
        }
        return topics;
    }

    public synchronized List<Note> getNotes() {
        if (notes == null && note != null && note.getMeetingId() != null) {
            notes = noteService.listByMeetingId(Id.of(Meeting.class, note.getMeetingId()));
        }
        return notes;
    }
}
