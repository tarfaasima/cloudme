package org.cloudme.notepad.guice;

import org.cloudme.notepad.meeting.MeetingModule;
import org.cloudme.notepad.note.NoteModule;

import com.google.inject.Module;

public interface GuiceModules {
	static final Module[] MODULES = { new MeetingModule(), new NoteModule() };
}
