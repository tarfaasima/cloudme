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
import org.cloudme.notepad.stripes.validation.SimpleDateConverter;
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
	@Validate(required = true, converter = SimpleDateConverter.class)
	private Date date;
    @Validate( required = true ) private String topic;

    @DefaultHandler
    @DontValidate
    public Resolution create() {
        if (note == null || note.getMeetingId() == null) {
            date = Calendar.getInstance(getContext().getLocale()).getTime();
        }
        else {
            loadDateAndTopic(note);
            note.setId(null);
        }
        return resolve("note.jsp");
    }

    @DontValidate
    public Resolution edit() {
        if (note == null || note.getId() == null) {
            return create();
        }
        note = noteService.find(note.getId());
        loadDateAndTopic(note);
        return resolve("note.jsp");
    }

    public Resolution save() {
        note.setDueDate(dateService.convert(dueDate, date));
		if (note.getId() != null) {
            meetingService.update(note, date, topic);
        }
        else {
            meetingService.create(note, date, topic);
        }
		return redirectSelf("create", param("note.meetingId", note.getMeetingId()));
    }

    @DontValidate
    public Resolution delete() {
        meetingService.remove(Id.of(Meeting.class, note.getMeetingId()), Id.of(note));
        return redirectSelf("create", param("note.meetingId", note.getMeetingId()));
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

    private void loadDateAndTopic(Note note) {
        val meeting = meetingService.find(Id.of(Meeting.class, note.getMeetingId()));
        if (meeting != null) {
            date = meeting.getDate();
            topic = meeting.getTopic();
        }
        else {
            date = new Date();
        }
    }
}
