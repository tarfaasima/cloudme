package org.cloudme.runtrack.layout;

import java.util.ArrayList;
import java.util.Collection;

import org.cloudme.runtrack.model.Track;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.touchkit.TouchLayout;
import com.vaadin.ui.Form;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class EntryLayout extends TouchLayout {
	private Collection<Track> tracks;

	{
		tracks = new ArrayList<Track>();
		tracks.add(new Track() {
			{
				setName("Running Business");
				setDistance(10);
			}
		});
		tracks.add(new Track() {
			{
				setName("Kleine Strecke Bergedorf");
				setDistance(7.4F);
			}
		});
	}

	public EntryLayout() {
		setCaption("Enter Training");
		addComponent(new Label("Enter your training data", Label.CONTENT_TEXT));

		TextField distanceField = new TextField("Distance") {
			{
				setReadOnly(true);
			}
		};
		final ListSelect trackField = new ListSelect();
		trackField.setData(tracks);
		trackField.addListener(new ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				trackField.setValue(((Track) event.getProperty().getValue())
						.getDistance()
						+ " km");
			}
		});

		Form form = new Form();
		form.addField("track", trackField);
		form.addField("distance", distanceField);
		addComponent(form);
	}
}
