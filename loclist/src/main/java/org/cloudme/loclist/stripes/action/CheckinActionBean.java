package org.cloudme.loclist.stripes.action;

import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cloudme.gaestripes.AbstractActionBean;
import org.cloudme.loclist.item.ItemService;
import org.cloudme.loclist.location.LocationService;
import org.cloudme.loclist.model.ItemInstance;

import com.google.inject.Inject;

@UrlBinding( "/action/checkin/{itemListId}/{latitude}/{longitude}" )
public class CheckinActionBean extends AbstractActionBean {
    private static final Log LOG = LogFactory.getLog(CheckinActionBean.class);
    @Inject
    private LocationService locationService;
    @Inject
    private ItemService itemService;
    private Long itemListId;
    private float latitude;
    private float longitude;
    private List<ItemInstance> itemInstances;

    @DefaultHandler
    public Resolution show() {
        Long checkinId = locationService.checkin(latitude, longitude).getId();
        LOG.info(String.format("checkinId = %d", checkinId));
        setItemInstances(itemService.getItemInstances(checkinId, itemListId));
        return resolve("checkin");
    }

    public Long getItemListId() {
        return itemListId;
    }

    public void setItemListId(Long itemListId) {
        this.itemListId = itemListId;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setItemInstances(List<ItemInstance> itemInstances) {
        this.itemInstances = itemInstances;
    }

    public List<ItemInstance> getItemInstances() {
        return itemInstances;
    }
}
