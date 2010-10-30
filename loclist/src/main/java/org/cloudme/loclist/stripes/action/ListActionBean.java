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

@UrlBinding( "/action/list/{$event}/{id}" )
public class ListActionBean extends AbstractActionBean {
    @Inject
    private ItemService itemService;
    private List<ItemList> itemLists;
    @ValidateNestedProperties( { @Validate( field = "name", required = true ) } )
    private ItemList itemList;
    private Long id;

    public void setItemLists(List<ItemList> itemLists) {
        this.itemLists = itemLists;
    }

    public List<ItemList> getItemLists() {
        return itemLists;
    }

    @DontValidate
    public Resolution show() {
        itemList = itemService.getItemList(id);
        return resolve("listShow.jsp");
    }

    @DontValidate
    public Resolution create() {
        return resolve("listCreate.jsp");
    }

    @DontValidate
    public Resolution delete() {
        itemService.deleteItemList(id);
        return new RedirectResolution(getClass());
    }

    @DefaultHandler
    @DontValidate
    public Resolution index() {
        itemLists = itemService.getItemLists();
        return resolve("listIndex.jsp");
    }

    public Resolution save() {
        itemService.put(itemList);
        return new RedirectResolution(getClass());
    }

    public void setItemList(ItemList itemList) {
        this.itemList = itemList;
    }

    public ItemList getItemList() {
        return itemList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
