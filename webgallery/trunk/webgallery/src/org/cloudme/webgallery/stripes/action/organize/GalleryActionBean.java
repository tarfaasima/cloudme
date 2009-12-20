package org.cloudme.webgallery.stripes.action.organize;

import java.util.List;

import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import org.cloudme.webgallery.Gallery;

@UrlBinding("/organize/gallery/{$event}/{id}")
public class GalleryActionBean extends AbstractOrganizeActionBean<String, Gallery> {
    @ValidateNestedProperties({
        @Validate(field="name", required=true)
    })
    private List<Gallery> items;

	public GalleryActionBean() {
		super("/organize/gallery");
	}

    @Override
    public void setItems(List<Gallery> galleries) {
        items = galleries;
    }

    @Override
    public List<Gallery> getItems() {
        return items;
    }
}
