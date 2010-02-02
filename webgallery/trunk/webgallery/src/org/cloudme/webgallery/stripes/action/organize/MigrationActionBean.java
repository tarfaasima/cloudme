package org.cloudme.webgallery.stripes.action.organize;

import java.io.IOException;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.cloudme.webgallery.model.migration.MigrationService;
import org.cloudme.webgallery.stripes.action.AbstractActionBean;

@UrlBinding("/organize/migration")
public class MigrationActionBean extends AbstractActionBean {
    @SpringBean
    private MigrationService service;
    
    @DefaultHandler
	public String migrate() throws IOException {
        String log = service.migrate();
		getContext().getResponse().getOutputStream().println(log);
		return null;
    }
}
