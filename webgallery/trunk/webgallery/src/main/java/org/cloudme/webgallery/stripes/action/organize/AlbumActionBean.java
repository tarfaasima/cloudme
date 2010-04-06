package org.cloudme.webgallery.stripes.action.organize;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import org.cloudme.webgallery.message.Message;
import org.cloudme.webgallery.model.Album;
import org.cloudme.webgallery.service.AlbumService;
import org.cloudme.webgallery.stripes.action.AbstractActionBean;

@UrlBinding("/organize/album/{$event}/{id}")
public class AlbumActionBean extends AbstractActionBean {
    private Long id;
    @ValidateNestedProperties( { @Validate(field = "name", required = true) })
    private List<Album> items;
    @SpringBean
    private AlbumService service;

    @DontValidate
    public Resolution delete() {
        service.delete(id);
        addMessage(new Message("Album {0} deleted successfully.", id));
        return new RedirectResolution(getClass());
    }

    @DefaultHandler
    @DontValidate
    public Resolution edit() {
        setItems(new ArrayList<Album>(service.findAll()));
        return new ForwardResolution(getJspPath("/organize/album"));
    }

    public Long getId() {
        return id;
    }

    public List<Album> getItems() {
        return items;
    }

    public Resolution save() {
        for (Album album : items) {
            service.save(album);
        }
        addMessage(new Message("{0} albums saved successfully.", items == null ? 0 : items.size()));
        return new RedirectResolution(getClass());
    }

    public void setId(Long id) {
        this.id = id;
    }

	public void setItems(List<Album> items) {
		this.items = items;
    }
}
