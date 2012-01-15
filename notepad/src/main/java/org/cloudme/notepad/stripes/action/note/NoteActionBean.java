package org.cloudme.notepad.stripes.action.note;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
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

import com.google.inject.Inject;

@Getter
@Setter
@UrlBinding( "/app/note/{$event}/{id}" )
public class NoteActionBean extends AbstractActionBean {
    @Inject private MeetingService meetingService;
    @Inject private NoteService noteService;
    @Inject private DateService dateService;
    private Set<String> topics;
    @ValidateNestedProperties( { @Validate( field = "content", required = true ) } ) private Note note;
    @Validate( required = true ) private Date date;
    @Validate( required = true ) private String topic;
    private Long id;
    private String dueDate;

    @DefaultHandler
    @DontValidate
    public Resolution create() {
        if (id == null) {
            date = Calendar.getInstance(getContext().getLocale()).getTime();
        }
        else {
            loadDateAndTopic(id);
        }
        return resolve("note.jsp");
    }

    private void loadDateAndTopic(Long id) {
        Meeting meeting = meetingService.find(id);
        if (meeting != null) {
            date = meeting.getDate();
            topic = meeting.getTopic();
        }
    }

    @DontValidate
    public Resolution edit() {
        if (id == null) {
            return create();
        }
        note = noteService.find(id);
        if (note == null) {
            return create();
        }
        loadDateAndTopic(note.getMeetingId());
        return resolve("note.jsp");
    }

    public Resolution save() {
        note.setDueDate(dateService.convert(dueDate, date));
        if (note.getId() == null) {
            meetingService.create(note, date, topic);
            return redirect(getClass(), "create").addParameter("id", note.getMeetingId());
        }
        else {
            meetingService.update(note, date, topic);
            return redirect(getClass(), "edit").addParameter("id", note.getId());
        }
    }

    public Set<String> getTopics() {
        if (topics == null) {
            topics = meetingService.findAllTopics();
        }
        return topics;
    }
}
