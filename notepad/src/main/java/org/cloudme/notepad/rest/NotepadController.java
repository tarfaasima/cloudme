package org.cloudme.notepad.rest;

import javax.servlet.ServletException;

import org.cloudme.wrestle.Controller;

public class NotepadController extends Controller {
    @Override
    public void init() throws ServletException {
        register(new TopicHandler());
    }
}
