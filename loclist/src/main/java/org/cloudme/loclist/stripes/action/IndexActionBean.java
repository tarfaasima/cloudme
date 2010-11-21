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
import org.cloudme.loclist.model.ItemList;

import com.google.inject.Inject;

@UrlBinding( "/action/index/{$event}" )
public class IndexActionBean extends AbstractActionBean {
    @Inject
    private ItemService itemService;
    private List<ItemList> itemLists;
    @ValidateNestedProperties( { @Validate( field = "name", required = true ) } )
    private ItemList itemList;

    @DefaultHandler
    @DontValidate
    public Resolution view() {
        itemLists = itemService.getItemLists();
        return resolve("index.jsp");
    }

    public Resolution create() {
        itemService.put(itemList);
        return new RedirectResolution("/action/index");
    }

    public void setItemLists(List<ItemList> itemLists) {
        this.itemLists = itemLists;
    }

    public List<ItemList> getItemLists() {
        return itemLists;
    }

    public void setItemList(ItemList itemList) {
        this.itemList = itemList;
    }

    public ItemList getItemList() {
        return itemList;
    }
}
