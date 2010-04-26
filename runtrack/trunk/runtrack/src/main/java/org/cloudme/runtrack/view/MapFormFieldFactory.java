package org.cloudme.runtrack.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.vaadin.data.Item;
import com.vaadin.data.util.PropertyFormatter;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormFieldFactory;

@SuppressWarnings("serial")
public class MapFormFieldFactory implements FormFieldFactory {
	private final Map<Object, Field> fieldMap = new HashMap<Object, Field>();
	private final Collection<Object> fieldOrder = new ArrayList<Object>();
	private final Map<Object, FormatterDelegate> formatterMap = new HashMap<Object, FormatterDelegate>();

	@SuppressWarnings("unchecked")
	public Field createField(Item item, Object propertyId, Component uiContext) {
		Field field = fieldMap.get(propertyId.toString());
		if (formatterMap.containsKey(propertyId)) {
			final FormatterDelegate formatter = formatterMap.get(propertyId);
			field.setPropertyDataSource(new PropertyFormatter(item
					.getItemProperty(propertyId)) {
				@Override
				public Object parse(String formattedValue) throws Exception {
					return formatter.parse(formattedValue);
				}

				@Override
				public String format(Object value) {
					return formatter.format(value);
				}
			});
		}
		return field;
	}

	public void addField(Object propertyId, Field field) {
		fieldMap.put(propertyId, field);
		fieldOrder.add(propertyId);
	}

	public Collection<Object> getFieldOrder() {
		return fieldOrder;
	}

	public void addField(Object propertyId, Field field,
			FormatterDelegate formatter) {
		addField(propertyId, field);
		formatterMap.put(propertyId, formatter);
	}
}
