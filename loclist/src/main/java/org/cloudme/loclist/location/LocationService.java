package org.cloudme.loclist.location;

import org.cloudme.loclist.dao.CheckinDao;
import org.cloudme.loclist.dao.LocationDao;
import org.cloudme.loclist.model.Checkin;
import org.cloudme.loclist.model.Location;

import com.google.inject.Inject;

public class LocationService {
    @Inject
    private LocationDao locationDao;
    @Inject
    private CheckinDao checkinDao;
    /**
     * The radius in kilometers of tolerance to map a checkin to an existing
     * location within this radius.
     */
    private double radius = 0.05d;

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * Checks in at the given geo location. If a location with this coordinates
     * or in the distance no longer than the {@link #radius} already exists, the
     * existing location is returned.
     * 
     * @param latitude
     *            The latitude of the geo location.
     * @param longitude
     *            The longitude of the geo location.
     * @return The nearest location (if it already exists) or a location with
     *         the given coordinates.
     */
    public Location checkin(float latitude, float longitude) {
        Location result = null;
        double dMin = Double.MAX_VALUE;
        for (Location location : locationDao.findAll()) {
            double d = distance(latitude, longitude, location.getLatitude(), location.getLongitude());
            if (d < radius && d < dMin) {
                result = location;
                dMin = d;
            }
        }
        if (result == null) {
            result = new Location(latitude, longitude);
            locationDao.save(result);
        }
        Checkin checkin = new Checkin();
        checkin.setLocationId(result.getId());
        checkin.setTimestamp(System.currentTimeMillis());
        checkin.setLatitude(latitude);
        checkin.setLongitude(longitude);
        checkinDao.save(checkin);
        return result;
    }

    /**
     * http://www.movable-type.co.uk/scripts/latlong.html
     * 
     * @param lat1
     *            Latitude of first location.
     * @param lon1
     *            Longitude of first location.
     * @param lat2
     *            Latitude of second location.
     * @param lon2
     *            Longitude of second location.
     * @return The distance between two locations in km.
     */
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);
        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2)
                * Math.cos(lon1 - lon2));
        return 6371.0f * angle;
    }
}
