package org.cloudme.notepad.rest;

import javax.servlet.ServletException;

import org.cloudme.notepad.date.DateModule;
import org.cloudme.notepad.export.ExportModule;
import org.cloudme.notepad.meeting.MeetingModule;
import org.cloudme.notepad.note.NoteModule;
import org.cloudme.wrestle.Controller;

public class NotepadController extends Controller {
    @Override
    public void init() throws ServletException {
        register(new DateModule(), new MeetingModule(), new NoteModule(), new ExportModule());

        register(new NoteHandler());
        register(new TaskHandler());
        register(new TopicHandler());
    }
}
