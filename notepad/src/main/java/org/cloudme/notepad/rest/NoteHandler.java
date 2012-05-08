package org.cloudme.notepad.rest;

import java.util.Date;

import org.cloudme.notepad.date.DateService;
import org.cloudme.notepad.meeting.Meeting;
import org.cloudme.notepad.meeting.MeetingService;
import org.cloudme.notepad.note.Note;
import org.cloudme.notepad.note.NoteService;
import org.cloudme.sugar.Id;
import org.cloudme.wrestle.ActionHandler;
import org.cloudme.wrestle.annotation.Get;
import org.cloudme.wrestle.annotation.Param;
import org.cloudme.wrestle.annotation.Post;
import org.cloudme.wrestle.annotation.UrlMapping;

import com.google.inject.Inject;

@UrlMapping( "/note" )
public class NoteHandler implements ActionHandler {
    @Inject private DateService dateService;
    @Inject private MeetingService meetingService;
    @Inject private NoteService noteService;

    @Get
    public Note find(Long id) {
        return noteService.find(Id.of(Note.class, id));
    }

    @Post
    public void save(@Param( name = "note" ) Note note,
            @Param( name = "topic" ) String topic,
            @Param( name = "dateStr" ) String dateStr,
            @Param( name = "dueDate" ) String dueDate) {
        Date date = dateService.convert(dateStr, new Date());
        note.setDueDate(dateService.convert(dueDate, date));
        if (note.getId() != null) {
            meetingService.update(note, date, topic);
        }
        else {
            meetingService.create(note, date, topic);
        }
    }

    @Get
    public void delete(Long noteId, Long meetingId) {
        meetingService.remove(Id.of(Meeting.class, meetingId), Id.of(Note.class, noteId));
    }

    @Get
    public void check(Long id) {
        noteService.toggleDone(Id.of(Note.class, id));
    }
}
