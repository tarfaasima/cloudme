package org.cloudme.loclist.location;

import org.cloudme.loclist.dao.LocationDao;
import org.cloudme.loclist.model.Location;

import com.google.inject.Inject;

public class LocationService {
    @Inject
    private LocationDao locationDao;

    public Location checkin(float latitude, float longitude) {
        for (Location location : locationDao.findAll()) {
            double d = distance(latitude, longitude, location.getLatitude(), location.getLongitude());
            if (d < 50.0d) {
                return location;
            }
        }
        return new Location(latitude, longitude);
    }

    /**
     * http://www.cs.princeton.edu/introcs/44st/Location.java.html
     * http://meinews
     * .niuz.biz/geographische-t85471.html?s=2496fadc816145c7e48344805ec2c251
     * http://de.wikipedia.org/wiki/Orthodrome
     * 
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);
        double angle = Math.acos(Math.sin(lat1)
                * Math.sin(lat2)
                + Math.cos(lat1)
                * Math.cos(lat2)
                * Math.cos(lon1 - lon2));
        return 111.111111111d * Math.toDegrees(angle);
    }
}
