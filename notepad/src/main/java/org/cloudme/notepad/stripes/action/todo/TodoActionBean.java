package org.cloudme.notepad.stripes.action.todo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.notepad.note.Note;
import org.cloudme.notepad.note.NoteService;
import org.cloudme.sugar.AbstractActionBean;

import com.google.inject.Inject;

@UrlBinding( "/app/todo/{$event}" )
@Getter
@Setter
public class TodoActionBean extends AbstractActionBean {
    @Inject NoteService noteService;
    private List<Note> todos;

    @DefaultHandler
    public Resolution list() {
        return resolve("todo.jsp");
    }

    public List<Note> getTodos() {
        if (todos == null) {
            todos = noteService.listAllTodos();
        }
        return todos;
    }
}
