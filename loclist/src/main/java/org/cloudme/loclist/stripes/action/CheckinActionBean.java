package org.cloudme.loclist.stripes.action;

import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.cloudme.gaestripes.AbstractActionBean;
import org.cloudme.loclist.item.ItemService;
import org.cloudme.loclist.location.LocationService;
import org.cloudme.loclist.model.ItemInstance;
import org.cloudme.loclist.model.ItemList;
import org.cloudme.loclist.stripes.validation.GeoCoordinateConverter;

import com.google.inject.Inject;

@UrlBinding( "/action/checkin/{itemListId}/{latitude}/{longitude}" )
public class CheckinActionBean extends AbstractActionBean {
    @Inject
    private LocationService locationService;
    @Inject
    private ItemService itemService;
    private Long itemListId;
    @Validate( converter = GeoCoordinateConverter.class )
    private float latitude;
    @Validate( converter = GeoCoordinateConverter.class )
    private float longitude;
    private List<ItemInstance> itemInstances;
    private ItemList itemList;

    @DefaultHandler
    public Resolution show() {
        Long checkinId = locationService.checkin(latitude, longitude).getId();
        itemInstances = itemService.getItemInstances(itemListId);
        itemService.orderByCheckin(checkinId, itemInstances);
        itemList = itemService.getItemList(itemListId);
        return resolve("checkin.jsp");
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

    public void setItemList(ItemList itemList) {
        this.itemList = itemList;
    }

    public ItemList getItemList() {
        return itemList;
    }
}
