package org.cloudme.loclist.stripes.action;

import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.gaestripes.AbstractActionBean;
import org.cloudme.loclist.item.ItemService;
import org.cloudme.loclist.model.ItemList;

import com.google.inject.Inject;

@UrlBinding( "/action/list/{$event}" )
public class ListActionBean extends AbstractActionBean {
    @Inject
    private ItemService itemService;
    private List<ItemList> itemLists;

    public void setItemLists(List<ItemList> itemLists) {
        this.itemLists = itemLists;
    }

    public List<ItemList> getItemLists() {
        return itemLists;
    }

    @DefaultHandler
    protected Resolution index() {
        setItemLists(itemService.getItemLists());
        return resolve("listIndex");
    }
}
