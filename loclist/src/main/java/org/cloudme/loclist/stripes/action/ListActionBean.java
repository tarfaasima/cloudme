package org.cloudme.loclist.stripes.action;

import java.util.List;

import net.sourceforge.stripes.action.RedirectResolution;
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

@UrlBinding( "/action/list/{$event}/{id}/{latitude}/{longitude}" )
public class ListActionBean extends AbstractActionBean {
    @Inject
    private LocationService locationService;
    @Inject
    private ItemService itemService;
    private Long id;
    @Validate( converter = GeoCoordinateConverter.class )
    private float latitude;
    @Validate( converter = GeoCoordinateConverter.class )
    private float longitude;
    private List<ItemInstance> itemInstances;
    private ItemList itemList;

    public Resolution checkin() {
        Long checkinId = locationService.checkin(latitude, longitude).getId();
        itemInstances = itemService.getItemInstances(id);
        itemService.orderByCheckin(checkinId, itemInstances);
        itemList = itemService.getItemList(id);
        return resolve("list.jsp");
    }

    public Resolution delete() {
        itemService.deleteItemList(id);
        return new RedirectResolution("/action/index");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
