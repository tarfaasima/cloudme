package org.cloudme.notepad.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.val;

public class DateService {
    private static final DateFormat[] FORMATS = { df("dd.MM.yy"), df("dd.MM.yyyy"), df("yyyy-MM-dd"), df("MM/dd/yy"),
            df("MM/dd/yyyy"), df("ddMMyy"), df("ddMMyyyy") };
    private static final Pattern DATE_WITHOUT_YEAR_PATTERN = Pattern
            .compile("(\\d{1,2})[\\.-/+# ]?(\\d{1,2})[\\.-/+# ]?");
    private static final Pattern DURATION_PATTERN = Pattern.compile("(\\d{1,2})(.*)");
    private final Set<String> WEEK_DURATION = new HashSet<String>(Arrays.asList("W",
            "WEEK",
            "WEEKS",
            "WK",
            "WOCHE",
            "WOCHEN"));
    private final Set<String> DAY_DURATION = new HashSet<String>(Arrays.asList("D",
            "DAY",
            "DAYS",
            "DY",
            "DYS",
            "T",
            "TAG",
            "TAGE"));

    public Date convert(String input, Date referenceDate) {
        if (input == null) {
            return null;
        }
        input = input.trim();
        if (input.length() == 0) {
            return null;
        }
        input = input.toUpperCase();
        for (val format : FORMATS) {
            try {
                return format.parse(input);
            }
            catch (ParseException e) {
                // can happen, try next
            }
        }
        try {
            return parseAsDay(input, referenceDate);
        }
        catch (NumberFormatException e) {
            // can happen, try next
        }
        try {
            return parseAsDateWithoutYear(input, referenceDate);
        }
        catch (NumberFormatException e) {
            // can happen, try next
        }
        try {
            return parseAsDuration(input, referenceDate);
        }
        catch (NumberFormatException e) {
            // can happen, try next
        }
        return null;
    }

    private Date parseAsDuration(String input, Date referenceDate) {
        Matcher matcher = DURATION_PATTERN.matcher(input);
        if (matcher.matches()) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(referenceDate);
            int value = Integer.parseInt(matcher.group(1));
            String duration = matcher.group(2).trim();
            if (WEEK_DURATION.contains(duration)) {
                cal.add(Calendar.WEEK_OF_YEAR, value);
                return cal.getTime();
            }
            if (DAY_DURATION.contains(duration)) {
                if (value > 5) {
                    value = value * 7 / 5;
                }
                cal.add(Calendar.DAY_OF_YEAR, value);
                int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                    cal.add(Calendar.DAY_OF_YEAR, 2);
                }
                return cal.getTime();
            }
        }
        throw new NumberFormatException();
    }

    private Date parseAsDateWithoutYear(String input, Date referenceDate) {
        Matcher matcher = DATE_WITHOUT_YEAR_PATTERN.matcher(input);
        if (matcher.matches()) {
            int day = Integer.parseInt(matcher.group(1));
            int month = Integer.parseInt(matcher.group(2));
            Calendar cal = Calendar.getInstance();
            cal.setTime(referenceDate);
            cal.set(Calendar.DAY_OF_MONTH, day);
            cal.set(Calendar.MONTH, month - 1);
            return incrementIfBefore(cal, referenceDate, Calendar.YEAR);
        }
        throw new NumberFormatException();
    }

    private Date parseAsDay(String input, Date referenceDate) {
        val day = Integer.parseInt(input);
        val cal = Calendar.getInstance();
        cal.setTime(referenceDate);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return incrementIfBefore(cal, referenceDate, Calendar.MONTH);
    }

    private Date incrementIfBefore(Calendar cal, Date referenceDate, int field) {
        val date = cal.getTime();
        if (referenceDate.after(date)) {
            cal.add(field, 1);
            return cal.getTime();
        }
        return date;
    }

    private static DateFormat df(String pattern) {
        return new SimpleDateFormat(pattern);
    }
}
