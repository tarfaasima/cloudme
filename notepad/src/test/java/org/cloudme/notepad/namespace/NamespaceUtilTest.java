package org.cloudme.notepad.namespace;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Iterator;

import org.cloudme.notepad.guice.GuiceModules;
import org.cloudme.notepad.note.Note;
import org.cloudme.notepad.note.NoteService;
import org.cloudme.sugar.AbstractServiceTestCase;
import org.junit.Test;

import com.google.appengine.api.NamespaceManager;
import com.google.inject.Inject;
import com.google.inject.Module;

public class NamespaceUtilTest extends AbstractServiceTestCase {
    @Inject NoteService noteService;

    @Test
    public void testAvailableNamespaces() {
        NamespaceManager.set("ns1");
        Note n1 = new Note();
        n1.setContent("Content 1");
        noteService.put(n1);

        NamespaceManager.set("ns2");
        Note n2 = new Note();
        n2.setContent("Content 2");
        noteService.put(n2);

        Iterator<String> it = NamespaceUtil.availableNamespaces().iterator();
        assertEquals("ns1", it.next());
        assertEquals("ns2", it.next());
        assertFalse(it.hasNext());
    }

    @Override
    protected Module[] getModules() {
        return GuiceModules.MODULES;
    }

}
