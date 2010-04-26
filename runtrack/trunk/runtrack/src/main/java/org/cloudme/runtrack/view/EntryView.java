package org.cloudme.runtrack.view;

import com.vaadin.touchkit.TouchLayout;
import com.vaadin.touchkit.TouchMenu;
import com.vaadin.touchkit.TouchMenu.TouchCommand;
import com.vaadin.touchkit.TouchMenu.TouchMenuItem;

@SuppressWarnings("serial")
public class EntryView extends TouchLayout {
	public EntryView() {
		setCaption("Routes");
		TouchMenu existingRoutesMenu = new TouchMenu();
		existingRoutesMenu.addItem("Kleine Runde Bergedorf",
				new TouchCommand() {
					public void itemTouched(TouchMenuItem item) {
					}
				});
		existingRoutesMenu.addItem("Running Business", new TouchCommand() {
			public void itemTouched(TouchMenuItem item) {
			}
		});
		addComponent(existingRoutesMenu);
		TouchMenu newRouteMenu = new TouchMenu();
		newRouteMenu.addItem("New Route", new TouchCommand() {
			public void itemTouched(TouchMenuItem item) {
				getParent().navigateTo(new NewRouteView());
			}
		});
		addComponent(newRouteMenu);

		//
		// TextField distanceField = new TextField("Distance") {
		// {
		// setReadOnly(true);
		// }
		// };
		// final ListSelect trackField = new ListSelect();
		// trackField.setData(routes);
		// trackField.addListener(new ValueChangeListener() {
		// public void valueChange(ValueChangeEvent event) {
		// trackField.setValue(((Route) event.getProperty().getValue())
		// .getDistance()
		// + " km");
		// }
		// });
		//
		// Form form = new Form();
		// form.addField("route", trackField);
		// form.addField("distance", distanceField);
		// addComponent(form);
	}
}
