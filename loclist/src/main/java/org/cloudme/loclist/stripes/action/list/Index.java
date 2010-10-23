package org.cloudme.loclist.stripes.action.list;

import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.gaestripes.AbstractActionBean;
import org.cloudme.loclist.item.ItemService;
import org.cloudme.loclist.model.ItemList;

import com.google.inject.Inject;

@UrlBinding( "/action/list/index" )
public class Index extends AbstractActionBean {
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
    protected Resolution show() {
        setItemLists(itemService.getItemLists());
        return resolve("Index");
    }
}
