package org.cloudme.runtrack.stripes;

import java.util.Collection;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import org.cloudme.runtrack.model.Route;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.helper.DAOBase;

public abstract class BaseActionBean<T> extends DAOBase implements ActionBean {
    static {
        ObjectifyService.register(Route.class);
    }

    private ActionBeanContext context;
    private final Class<T> clazz;
    private Long id;

    protected BaseActionBean(Class<T> clazz) {
        this.clazz = clazz;
    }

    public ActionBeanContext getContext() {
        return context;
    }

    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    @DefaultHandler
    public Resolution index() {
        return new ForwardResolution("");
    }

    public Resolution save() {
        ofy().put(getItem());
        return new ForwardResolution("");
    }

    public Resolution delete() {
        ofy().delete(clazz, id);
        return new ForwardResolution("");
    }

    public Resolution show() {
        setItem(ofy().get(clazz, id));
        return new ForwardResolution("");
    }

    @ValidationMethod( on = { "show", "delete" } )
    public void validateId(ValidationErrors errors) {
        if (id == null) {
            errors.add("id", new SimpleError("Id required."));
        }
    }

    public Collection<T> getItems() {
        return ofy().query(clazz).list();
    }

    public abstract T getItem();

    public abstract void setItem(T item);

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
