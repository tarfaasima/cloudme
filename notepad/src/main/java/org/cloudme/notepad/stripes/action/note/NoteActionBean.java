package org.cloudme.notepad.stripes.action.note;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import org.cloudme.notepad.date.DateService;
import org.cloudme.notepad.meeting.Meeting;
import org.cloudme.notepad.meeting.MeetingGroup;
import org.cloudme.notepad.meeting.MeetingService;
import org.cloudme.notepad.note.Note;
import org.cloudme.notepad.note.NoteService;
import org.cloudme.notepad.stripes.action.meeting.MeetingActionBean;
import org.cloudme.notepad.stripes.action.todo.TodoActionBean;
import org.cloudme.notepad.stripes.validation.SimpleDateConverter;
import org.cloudme.sugar.AbstractActionBean;
import org.cloudme.sugar.Id;

import com.google.inject.Inject;

@Getter
@Setter
@UrlBinding( "/app/note/{$event}/{note.meetingId}/{note.id}/{source}" )
public class NoteActionBean extends AbstractActionBean {
    @Inject private MeetingService meetingService;
    @Inject private NoteService noteService;
    @Inject private DateService dateService;
    private Iterable<String> topics;
    private List<Note> notes;
    private String dueDate;
    @ValidateNestedProperties( { @Validate( field = "content", required = true ) } ) private Note note;
    @Validate( required = true, converter = SimpleDateConverter.class ) private Date date;
    @Validate( required = true ) private String topic;
    private List<MeetingGroup> recentMeetings;
    private String source;

    @DefaultHandler
    @DontValidate
    public Resolution create() {
        if (note == null || note.getMeetingId() == null) {
            // date = Calendar.getInstance(getContext().getLocale()).getTime();
            date = new Date();
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
        return createRedirect(source, note.getMeetingId());
    }

    @DontValidate
    public Resolution delete() {
        boolean wholeMeetingDeleted = meetingService.remove(Id.of(Meeting.class, note.getMeetingId()), Id.of(note));
        return createRedirect(source, wholeMeetingDeleted ? null : note.getMeetingId());
    }

    @DontValidate
    public Resolution cancel() {
        if (note != null) {
            return createRedirect(source, note.getMeetingId());
        }
        return redirectSelf("create");
    }

    @DontValidate
    public Resolution check() {
        noteService.toggleDone(Id.of(note));
        return null;
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

    public synchronized List<MeetingGroup> getRecentMeetings() {
        if (recentMeetings == null) {
            recentMeetings = meetingService.findRecent();
        }
        return recentMeetings;
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

    private Resolution createRedirect(String source, Long meetingId) {
        if (MeetingActionBean.SOURCE.equalsIgnoreCase(source) && meetingId != null) {
            return new RedirectResolution(MeetingActionBean.class, "show").addParameter("meeting.id", meetingId);
        }
        if (TodoActionBean.SOURCE.equalsIgnoreCase(source)) {
            return new RedirectResolution(TodoActionBean.class);
        }
        if (meetingId != null) {
            return redirectSelf("create", param("note.meetingId", meetingId));
        }
        return redirectSelf("create");
    }
}
