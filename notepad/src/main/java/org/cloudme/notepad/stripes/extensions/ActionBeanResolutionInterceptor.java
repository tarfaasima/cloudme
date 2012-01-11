package org.cloudme.notepad.stripes.extensions;

import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

import org.cloudme.notepad.meeting.MeetingModule;
import org.cloudme.notepad.note.NoteModule;
import org.cloudme.sugar.AbstractActionBeanResolutionInterceptor;

import com.google.inject.Module;

@Intercepts( LifecycleStage.ActionBeanResolution )
public class ActionBeanResolutionInterceptor extends AbstractActionBeanResolutionInterceptor {
    private static final Module[] MODULES = { new MeetingModule(), new NoteModule() };

    @Override
    protected Module[] createModules() {
        return MODULES;
    }
}
