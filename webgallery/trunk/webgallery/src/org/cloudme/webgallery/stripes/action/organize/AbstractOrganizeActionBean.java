package org.cloudme.webgallery.stripes.action.organize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.cloudme.webgallery.IdObject;
import org.cloudme.webgallery.service.GenericService;
import org.cloudme.webgallery.stripes.util.AbstractActionBean;

@Deprecated
public abstract class AbstractOrganizeActionBean<K, T extends IdObject<K>> extends AbstractActionBean {
    @SpringBean
	private GenericService<K, T> service;
	private K id;
	private final String forwardPath;

	public AbstractOrganizeActionBean(String forwardPath) {
		this.forwardPath = forwardPath;
	}

    public K getId() {
        return id;
    }

    @DefaultHandler
    @DontValidate
    public Resolution edit() {
        setItems(toList(service.findAll()));
		return new ForwardResolution(getJspPath(forwardPath));
    }

    public Resolution save() {
        for (T item : getItems()) {
            service.save(item);
        }
        return new RedirectResolution(getClass());
    }

    @DontValidate
    public Resolution delete() {
        service.delete(id);
        return new RedirectResolution(getClass());
    }
    
    public void setId(K id) {
        this.id = id;
    }

    private List<T> toList(Collection<T> collection) {
        if (collection instanceof List<?>) {
            return (List<T>) collection;
        }
        return new ArrayList<T>(collection);
    }
    
    public abstract void setItems(List<T> list);
    
    public abstract List<T> getItems();

	protected GenericService<K, T> getService() {
		return service;
	}
}

