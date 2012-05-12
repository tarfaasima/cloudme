package org.cloudme.notepad.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cloudme.notepad.date.DateModule;
import org.cloudme.notepad.export.ExportModule;
import org.cloudme.notepad.meeting.MeetingModule;
import org.cloudme.notepad.note.NoteModule;
import org.cloudme.wrestle.WrestleController;
import org.cloudme.wrestle.annotation.UrlMapping;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.users.UserServiceFactory;

@UrlMapping( "api" )
public class NotepadController extends WrestleController {
    @Override
    public void init() throws ServletException {
        registerModules(new DateModule(), new MeetingModule(), new NoteModule(), new ExportModule());

        registerActionHandler(new NoteHandler());
        registerActionHandler(new TaskHandler());
        registerActionHandler(new TopicHandler());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NamespaceManager.set(UserServiceFactory.getUserService().getCurrentUser().getUserId());
        super.service(req, resp);
    }
}
