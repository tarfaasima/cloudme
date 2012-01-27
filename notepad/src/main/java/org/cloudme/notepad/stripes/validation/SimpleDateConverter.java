package org.cloudme.notepad.stripes.validation;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;

import org.cloudme.notepad.date.DateService;

public class SimpleDateConverter implements TypeConverter<Date> {
	private final DateService dateService = new DateService();

	@Override
	public void setLocale(Locale locale) {
		// ignored
	}

	@Override
	public Date convert(String input, Class<? extends Date> targetType, Collection<ValidationError> errors) {
		Date date = dateService.convert(input, new Date());
		if (date == null) {
			errors.add(new LocalizableError("validation.simpleDateConverter", input));
		}
		return date;
	}
}
