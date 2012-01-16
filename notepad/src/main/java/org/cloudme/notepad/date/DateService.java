package org.cloudme.notepad.date;

import static java.util.Arrays.asList;
import static java.util.regex.Pattern.compile;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.val;

/**
 * Conversion of dates and durations. This class is used to convert user input
 * to a date. The user can enter partial dates (e.g. without year or the day of
 * month only) or durations (e.g. "2 weeks").
 * <p>
 * A wide range of date formats is supported, in the following order:
 * <ol>
 * <li>dd.MM.yy</li>
 * <li>dd.MM.yyyy</li>
 * <li>yyyy-MM-dd</li>
 * <li>MM/dd/yy</li>
 * <li>MM/dd/yyyy</li>
 * <li>ddMMyy</li>
 * <li>ddMMyyyy</li>
 * </ol>
 * In addition, the user can enter only a single number, which will be
 * interpreted as day of month. If the day is smaller than the current day of
 * month, the month will be incremented by 1.
 * <p>
 * The date can be without year, and similar to the single number, it accepts
 * two numbers (with up to 2 digits) and various separator chars (space, ".",
 * "-", "/", "+", "#"). The year will be incremented by 1 if necessary.
 * <p>
 * The user can enter durations also, such as "1 week", "4 days". Following
 * durations are supported:
 * <ul>
 * <li>Week durations: W, WEEK, WEEKS, WK, WOCHE, WOCHEN</li>
 * <li>Day durations: D, DAY, DAYS, DY, DYS, T, TAG, TAGE</li>
 * </ul>
 * For day durations, weekends are ommitted and only working days are counted.
 * No holidays are considered.
 * 
 * @author Moritz Petersen
 */
public class DateService {
    private static final DateFormat[] FORMATS = { df("dd.MM.yy"), df("dd.MM.yyyy"), df("yyyy-MM-dd"), df("MM/dd/yy"),
            df("MM/dd/yyyy"), df("ddMMyy"), df("ddMMyyyy") };
	private static final Pattern DATE_WITHOUT_YEAR_PATTERN = compile("(\\d{1,2})[\\.-/+# ]?(\\d{1,2})[\\.-/+# ]?");
	private static final Pattern DURATION_PATTERN = compile("(\\d{1,2})(.*)");
	private final Set<String> WEEK_DURATION = new HashSet<String>(asList("W", "WEEK", "WEEKS", "WK", "WOCHE",
            "WOCHEN"));
	private final Set<String> DAY_DURATION = new HashSet<String>(asList("D", "DAY", "DAYS", "DY", "DYS", "T",
            "TAG", "TAGE"));

    /**
     * Converts user input to a {@link Date}.
     * 
     * @param input
     *            The user input (date, partial date, duration).
     * @param referenceDate
     *            The reference date.
     * @return The converted date or <tt>null</tt> if conversion is not
     *         possible.
     */
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
