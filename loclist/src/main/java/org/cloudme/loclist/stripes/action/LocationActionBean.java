package org.cloudme.loclist.stripes.action;

import java.util.Collection;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.loclist.location.Location;
import org.cloudme.loclist.location.LocationService;
import org.cloudme.sugar.AbstractActionBean;

import com.google.inject.Inject;

@UrlBinding( "/action/location/{$event}/{id}" )
public class LocationActionBean extends AbstractActionBean {
    @Inject
    private LocationService locationService;
    private Collection<Location> locations;
    private Long id;
    private Location location;

    @DefaultHandler
    public Resolution show() {
        locations = locationService.findAllLocations();
        return resolve("location.jsp");
    }

    public Resolution delete() {
        if (id != null) {
            locationService.delete(id);
        }
        return show();
    }

    public Resolution details() {
        location = locationService.findWithItemIndexs(id);
        return resolve("details.jsp");
    }

    public void setLocations(Collection<Location> locations) {
        this.locations = locations;
    }

    public Collection<Location> getLocations() {
        return locations;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
