package org.cloudme.loclist.dao;

import org.cloudme.gaestripes.AbstractDao;
import org.cloudme.loclist.model.Location;

public class LocationDao extends AbstractDao<Location> {
    public LocationDao() {
        super(Location.class);
    }
}
