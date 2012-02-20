package org.cloudme.notepad.stripes.action.todo;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.notepad.export.excel.ExcelExportService;
import org.cloudme.notepad.meeting.Meeting;
import org.cloudme.notepad.meeting.MeetingService;
import org.cloudme.notepad.note.Note;
import org.cloudme.notepad.note.NoteService;
import org.cloudme.sugar.AbstractActionBean;

import com.google.inject.Inject;

@UrlBinding( "/app/todo/{$event}" )
@Getter
@Setter
public class TodoActionBean extends AbstractActionBean {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
    @Inject ExcelExportService excelExportService;
    @Inject MeetingService meetingService;
    @Inject NoteService noteService;
    private List<Note> todos;

    @DefaultHandler
    public Resolution list() {
        return resolve("todo.jsp");
    }

    public Resolution export() throws IOException {
        Map<Long, Meeting> meetingMap = meetingService.findAllAsMap();
        HttpServletResponse response = getContext().getResponse();
        response.setHeader("Content-Disposition", "attachment;filename=\"Task-List_"
                + DATE_FORMAT.format(new Date())
                + "\"");
        response.setContentType(excelExportService.getContentType());
        excelExportService.export(meetingMap, noteService.listAllTodos(), response.getOutputStream());
        return null;
    }

    public List<Note> getTodos() {
        if (todos == null) {
            todos = noteService.listOpenTodos();
        }
        return todos;
    }
}
