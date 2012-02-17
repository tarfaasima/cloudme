package org.cloudme.notepad.stripes.action.admin;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.notepad.namespace.NamespaceUtil;
import org.cloudme.notepad.note.NoteService;
import org.cloudme.sugar.AbstractActionBean;

import com.google.appengine.api.NamespaceManager;
import com.google.inject.Inject;

@UrlBinding( "/app/admin/migration/{$event}" )
@Getter
@Setter
public class MigrationActionBean extends AbstractActionBean {
    @Inject private NoteService noteService;
    private Collection<String> namespaces;

    @DefaultHandler
    public Resolution execute() {
        namespaces = NamespaceUtil.availableNamespaces();
        String currentNamespace = NamespaceManager.get();
        for (String namespace : namespaces) {
            NamespaceManager.set(namespace);
            noteService.put(noteService.findAll());
        }
        NamespaceManager.set(currentNamespace);
        return resolve("migration.jsp");
    }
}
