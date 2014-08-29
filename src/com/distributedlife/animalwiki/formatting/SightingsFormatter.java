package com.distributedlife.animalwiki.formatting;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;

import java.util.HashMap;
import java.util.Map;

public class SightingsFormatter {
    private static final String PATTERN = "YYYY-MM-dd";
    private DateTimeParser parser = DateTimeFormat.forPattern(PATTERN).getParser();
    private DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(parser).toFormatter();
    private Map<Integer, String> mapping = new HashMap<Integer, String>();

    public SightingsFormatter() {
        mapping.put(1, "Monday");
        mapping.put(2, "Tuesday");
        mapping.put(3, "Wednesday");
        mapping.put(4, "Thursday");
        mapping.put(5, "Friday");
        mapping.put(6, "Saturday");
        mapping.put(7, "Sunday");
        mapping.put(8, "Last Monday");
        mapping.put(9, "Last Tuesday");
        mapping.put(10, "Last Wednesday");
        mapping.put(11, "Last Thursday");
        mapping.put(12, "Last Friday");
        mapping.put(13, "Last Saturday");
        mapping.put(14, "Last Sunday");
    }

    public String getDisplayString(DateTime dateTime) {
        DateTime dateOnly = removeTimePart(dateTime);
        DateTime today = removeTimePart(DateTime.now());
        DateTime yesterday = removeTimePart(DateTime.now().minusDays(1));
        DateTime lastWeek = removeTimePart(DateTime.now().minusDays(7));
        DateTime lastFortnight = removeTimePart(DateTime.now().minusDays(14));

        if (dateOnly.equals(today)) {
            return "Today";
        } else if (dateOnly.equals(yesterday)) {
            return "Yesterday";
        } else if (dateOnly.isAfter(lastWeek)) {
            return dayOfWeekMapping(dateOnly.getDayOfWeek());
        } else if (dateOnly.isAfter(lastFortnight)) {
            return dayOfWeekMapping(dateOnly.getDayOfWeek() + 7);
        } else {
            return dateOnly.toString(formatter);
        }
    }

    private DateTime removeTimePart(DateTime dateTime) {
        return dateTime.minusMillis(dateTime.getMillisOfSecond())
                .minusSeconds(dateTime.getSecondOfMinute())
                .minusMinutes(dateTime.getMinuteOfHour())
                .minusHours(dateTime.getHourOfDay());
    }

    private String dayOfWeekMapping(int dayOfWeek) {
        return mapping.get(dayOfWeek);
    }
}