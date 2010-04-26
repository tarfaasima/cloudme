package org.cloudme.runtrack.view;

public interface FormatterDelegate {
	Object parse(String formattedValue);

	String format(Object value);
}
