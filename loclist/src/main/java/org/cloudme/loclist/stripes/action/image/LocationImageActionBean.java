package org.cloudme.loclist.stripes.action.image;

import java.io.IOException;

import javax.servlet.ServletOutputStream;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.loclist.location.Location;
import org.cloudme.loclist.location.LocationService;
import org.cloudme.sugar.AbstractActionBean;

import com.google.inject.Inject;

@UrlBinding( "/action/image/location_{id}.jpg" )
public class LocationImageActionBean extends AbstractActionBean {
    @Inject
    private LocationService locationService;
    private Long id;

    @DefaultHandler
    public Resolution show() throws IOException {
        ServletOutputStream out = getContext().getResponse().getOutputStream();
        Location location = locationService.findWithThumbnail(id);
        out.write(location.getThumbnailBytes());
        return null;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
