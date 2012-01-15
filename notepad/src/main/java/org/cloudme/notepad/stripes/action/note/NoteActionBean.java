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
    @Inject
    private MeetingService meetingService;
    @Inject
    private NoteService noteService;
    @Inject
    private DateService dateService;
    private Set<String> topics;
    @ValidateNestedProperties( { @Validate( field = "content", required = true ) } )
    private Note note;
    @Validate( required = true )
    private Date date;
    @Validate( required = true )
    private String topic;
    private Long id;
    private String dueDate;

    @DefaultHandler
    @DontValidate
    public Resolution edit() {
        if (id == null) {
            date = Calendar.getInstance(getContext().getLocale()).getTime();
        }
        else {
            Meeting meeting = meetingService.find(id);
            date = meeting.getDate();
            topic = meeting.getTopic();
        }
        return resolve("note.jsp");
    }

    public Resolution save() {
        note.setDueDate(dateService.convert(dueDate, date));
        noteService.put(note, date, topic);
        return redirect("/app/note/edit/" + note.getMeetingId());
    }

    public Set<String> getTopcis() {
        if (topics == null) {
            topics = meetingService.findAllTopics();
        }
        return topics;
    }
}
