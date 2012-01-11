package org.cloudme.notepad.stripes.action.note;

import java.util.Date;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import org.cloudme.notepad.meeting.MeetingService;
import org.cloudme.notepad.note.Note;
import org.cloudme.notepad.note.NoteService;
import org.cloudme.sugar.AbstractActionBean;

import com.google.inject.Inject;

@Getter
@Setter
@UrlBinding( "/app/note/{$event}" )
public class NoteActionBean extends AbstractActionBean {
    @Inject
    private MeetingService meetingService;
    private NoteService noteService;
    private Set<String> topics;
    @ValidateNestedProperties( { @Validate( field = "content", required = true ) } )
    private Note note;
    @Validate( required = true )
    private Date date;
    @Validate( required = true )
    private String topic;

    @DefaultHandler
    @DontValidate
    public Resolution edit() {
        topics = meetingService.findAllTopics();
        return resolve("note.jsp");
    }

    public Resolution save() {
        noteService.put(note);
        return edit();
    }

    public String getTopicsAsString() {
        StringBuilder sb = new StringBuilder();
        for (val topic : topics) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append('"');
            sb.append(topic);
            sb.append('"');
        }
        return sb.toString();
    }
}
