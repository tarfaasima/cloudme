package org.cloudme.loclist.stripes.action;

import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import org.cloudme.gaestripes.AbstractActionBean;
import org.cloudme.loclist.item.ItemService;
import org.cloudme.loclist.model.Item;
import org.cloudme.loclist.model.ItemInstance;

import com.google.inject.Inject;

@UrlBinding( "/action/item/{itemListId}/{$event}/{id}" )
public class ItemActionBean extends AbstractActionBean {
    @Inject
    private ItemService itemService;
    private Long id;
    private Long itemListId;
    @ValidateNestedProperties( { @Validate( field = "text", required = true ) } )
    private Item item;
    private List<Item> items;
    private List<ItemInstance> itemInstances;
    
    @DontValidate
    @DefaultHandler
    public Resolution index() {
        itemInstances = itemService.getItemInstancesByItemList(itemListId);
        items = itemService.getItemsNotInList(itemInstances);
        return resolve("itemIndex.jsp");
    }

    public Resolution save() {
        itemService.createItem(itemListId, item);
        return new RedirectResolution("/action/item/" + itemListId);
    }

    @DontValidate
    public Resolution delete() {
        itemService.deleteItem(id);
        return new RedirectResolution("/action/item/" + itemListId);
    }

    @DontValidate
    public Resolution add() {
        itemService.createItemInstance(itemListId, id);
        return new RedirectResolution("/action/item/" + itemListId);
    }

    @DontValidate
    public Resolution remove() {
        itemService.deleteItemInstance(id);
        return new RedirectResolution("/action/item/" + itemListId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemListId() {
        return itemListId;
    }

    public void setItemListId(Long itemListId) {
        this.itemListId = itemListId;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItemInstances(List<ItemInstance> itemInstances) {
        this.itemInstances = itemInstances;
    }

    public List<ItemInstance> getItemInstances() {
        return itemInstances;
    }
}
