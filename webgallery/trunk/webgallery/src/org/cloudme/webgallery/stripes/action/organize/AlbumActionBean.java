package org.cloudme.webgallery.stripes.action.organize;

import java.util.List;

import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import org.cloudme.webgallery.Album;

@UrlBinding("/organize/album/{$event}/{id}")
public class AlbumActionBean extends AbstractOrganizeActionBean<String, Album> {
    @ValidateNestedProperties({
        @Validate(field="name", required=true)
    })
    private List<Album> items;

	public AlbumActionBean() {
		super("/organize/album");
	}

    @Override
    public void setItems(List<Album> galleries) {
        items = galleries;
    }

    @Override
    public List<Album> getItems() {
        return items;
    }
}
