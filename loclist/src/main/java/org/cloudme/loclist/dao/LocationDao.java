package org.cloudme.loclist.dao;

import org.cloudme.loclist.model.Location;

public class LocationDao extends BaseDao<Location> {
    public LocationDao() {
        super(Location.class);
    }
}
