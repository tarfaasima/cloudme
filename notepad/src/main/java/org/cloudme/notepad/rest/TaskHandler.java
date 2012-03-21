package org.cloudme.notepad.rest;

import java.util.List;

import org.cloudme.notepad.note.Note;
import org.cloudme.notepad.note.NoteService;
import org.cloudme.wrestle.ActionHandler;
import org.cloudme.wrestle.annotation.Get;
import org.cloudme.wrestle.annotation.UrlMapping;

import com.google.inject.Inject;

@UrlMapping( "/task" )
public class TaskHandler implements ActionHandler {
    @Inject NoteService noteService;

    @Get
    public List<Note> list() {
        return noteService.listOpenTodos();
    }
}
