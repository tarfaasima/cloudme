/**
 * 
 */
package org.cloudme.runtrack.view;

import com.vaadin.data.util.PropertyFormatter;

@SuppressWarnings( { "unchecked", "serial" })
final class NullablePropertyFormatter extends PropertyFormatter {
	NullablePropertyFormatter() {
		super(null);
	}

	@Override
	public Object parse(String value) throws Exception {
		value = value.trim();
		return value.length() == 0 ? null : value;
	}

	@Override
	public String format(Object value) {
		return value == null ? "" : value.toString();
	}
}