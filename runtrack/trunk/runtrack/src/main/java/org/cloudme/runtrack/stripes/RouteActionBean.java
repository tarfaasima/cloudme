package org.cloudme.runtrack.stripes;

import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.runtrack.model.Route;

@UrlBinding( "/route/{$event}/{id}" )
public class RouteActionBean extends BaseActionBean<Route> {
    private Route route;

    protected RouteActionBean() {
        super(Route.class);
    }

    @Override
    public Route getItem() {
        return route;
    }

    @Override
    public void setItem(Route route) {
        this.route = route;
    }
}
