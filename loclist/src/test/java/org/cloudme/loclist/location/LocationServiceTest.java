package org.cloudme.loclist.location;

import static org.junit.Assert.assertEquals;

import org.cloudme.loclist.dao.LocationDao;
import org.cloudme.loclist.model.Location;
import org.cloudme.loclist.test.AbstractServiceTestCase;
import org.junit.Test;

import com.google.inject.Inject;

public class LocationServiceTest extends AbstractServiceTestCase {
    @Inject
    private LocationService locationService;
    @Inject
    private LocationDao locationDao;

    @Test
    public void testLocationService() {
        locationService.setRadius(300);
        Location manchester = locationService.checkin(53.480712f, -2.234376f);
        Location london1 = locationService.checkin(51.500152f, -0.126236f);
        assertEquals(manchester.getGeoPt(), london1.getGeoPt());
        locationService.setRadius(200);
        Location london2 = locationService.checkin(51.500152f, -0.126236f);
        assertEquals(new Location(51.500152f, -0.126236f).getGeoPt(), london2.getGeoPt());

        assertEquals(2, locationDao.listAll().size());
    }
}
