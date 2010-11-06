package org.cloudme.loclist.stripes.action;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import org.cloudme.gaestripes.AbstractActionBean;
import org.cloudme.loclist.item.ItemService;
import org.cloudme.loclist.item.Items;
import org.cloudme.loclist.model.Item;

import com.google.inject.Inject;

@UrlBinding( "/action/item/{itemListId}/{$event}/{id}" )
public class ItemActionBean extends AbstractActionBean {
    @Inject
    private ItemService itemService;
    private Long id;
    private Long itemListId;
    @ValidateNestedProperties( { @Validate( field = "text", required = true ) } )
    private Item item;
    private Items items;
    
    @DontValidate
    @DefaultHandler
    public Resolution index() {
        items = itemService.getItems(itemListId);
        return resolve("itemIndex.jsp");
    }

    public Resolution save() {
        itemService.put(item);
        return new RedirectResolution("/action/item/" + itemListId);
    }

    @DontValidate
    public Resolution delete() {
        itemService.deleteItem(id);
        return new RedirectResolution("/action/item/" + itemListId);
    }

    @DontValidate
    public Resolution add() {
        itemService.addToItemList(itemListId, id);
        return null;
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

    public void setItems(Items items) {
        this.items = items;
    }

    public Items getItems() {
        return items;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
