package org.cloudme.notepad.stripes.extensions;

import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.stripes.localization.DefaultLocalePicker;

public class NotepadLocalePicker extends DefaultLocalePicker {
	@Override
	public Locale pickLocale(HttpServletRequest request) {
		System.out.println("pickLocale()");
		for (@SuppressWarnings("unchecked")
		Enumeration<String> names = request.getHeaderNames(); names.hasMoreElements();) {
			String name = names.nextElement();
			System.out.println(name + ":" + request.getHeader(name));
		}
		return super.pickLocale(request);
	}
}
