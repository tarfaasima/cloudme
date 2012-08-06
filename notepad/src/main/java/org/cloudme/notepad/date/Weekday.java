package org.cloudme.notepad.date;

import java.util.Calendar;

enum Weekday {
	MONDAY(Calendar.MONDAY, "MO", "MON"), TUESDAY(Calendar.TUESDAY, "TU", "TUE", "DI", "DIE"), WEDNESDAY(
			Calendar.WEDNESDAY, "WE", "WED", "MI", "MIT"), THURSDAY(Calendar.THURSDAY, "TH", "THU", "DO", "DON"), FRIDAY(
			Calendar.FRIDAY, "FR", "FRI", "FRE"), SATURDAY(Calendar.SATURDAY, "SA", "SAT", "SAM"), SUNDAY(
			Calendar.SUNDAY, "SU", "SUN", "SO", "SON");

	public final String[] names;
	public final int dayOfWeek;

	private Weekday(int dayOfWeek, String... names) {
		this.dayOfWeek = dayOfWeek;
		this.names = names;
	}

	public boolean matches(String input) {
		for (String name : names) {
			if (name.equalsIgnoreCase(input)) {
				return true;
			}
		}
		return false;
	}
}
