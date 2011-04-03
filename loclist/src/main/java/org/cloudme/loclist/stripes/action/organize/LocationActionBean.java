package org.cloudme.loclist.stripes.action.organize;

import java.util.Collection;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.loclist.location.Location;
import org.cloudme.loclist.location.LocationService;
import org.cloudme.sugar.AbstractActionBean;

import com.google.inject.Inject;

@UrlBinding( "/action/organize/location" )
public class LocationActionBean extends AbstractActionBean {
    @Inject
    private LocationService locationService;
    private Collection<Location> locations;

    @DefaultHandler
    public Resolution show() {
        locations = locationService.findAllLocations();
        return resolve("location.jsp");
    }

    public void setLocations(Collection<Location> locations) {
        this.locations = locations;
    }

    public Collection<Location> getLocations() {
        return locations;
    }
}
