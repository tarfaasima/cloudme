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
	@Inject
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
		date = Calendar.getInstance(getContext().getLocale()).getTime();
        return resolve("note.jsp");
    }

    public Resolution save() {
        noteService.put(note);
		note = null;
        return edit();
    }
}
