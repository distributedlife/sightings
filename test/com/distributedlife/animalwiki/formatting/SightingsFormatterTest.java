package com.distributedlife.animalwiki.formatting;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SightingsFormatterTest {

    private SightingsFormatter sightingsFormatter;
    private Map<Integer, String> mapping = new HashMap<Integer, String>();

    @Before
    public void setup() {
        sightingsFormatter = new SightingsFormatter();
        mapping.put(1, "Monday");
        mapping.put(2, "Tuesday");
        mapping.put(3, "Wednesday");
        mapping.put(4, "Thursday");
        mapping.put(5, "Friday");
        mapping.put(6, "Saturday");
        mapping.put(7, "Sunday");
    }

    @Test
    public void shouldReturnTodayIfDateMatchesCurrent() {
        DateTime today = DateTime.now();

        assertThat(sightingsFormatter.getDisplayString(today), is("Today"));
    }

    @Test
    public void shouldReturnYesterdayIfDateMatchesCurrentMinusOneDay() {
        DateTime yesterday = DateTime.now().minusDays(1);

        assertThat(sightingsFormatter.getDisplayString(yesterday), is("Yesterday"));
    }

    @Test
    public void shouldReturnDayOfWeekIfOldThanYesterdayAndYoungerThan6DaysAgo() {
        DateTime minus2 = DateTime.now().minusDays(2);
        DateTime minus3 = DateTime.now().minusDays(3);
        DateTime minus4 = DateTime.now().minusDays(4);
        DateTime minus5 = DateTime.now().minusDays(5);
        DateTime minus6 = DateTime.now().minusDays(6);

        assertThat(sightingsFormatter.getDisplayString(minus2), is(mapping.get(minus2.getDayOfWeek())));
        assertThat(sightingsFormatter.getDisplayString(minus3), is(mapping.get(minus3.getDayOfWeek())));
        assertThat(sightingsFormatter.getDisplayString(minus4), is(mapping.get(minus4.getDayOfWeek())));
        assertThat(sightingsFormatter.getDisplayString(minus5), is(mapping.get(minus5.getDayOfWeek())));
        assertThat(sightingsFormatter.getDisplayString(minus6), is(mapping.get(minus6.getDayOfWeek())));
    }

    @Test
    public void shouldReturnLastDayOfWeekIfOldThan6daysAgoAndYoungerThanTwoWeeksAgo() {
        DateTime minus7 = DateTime.now().minusDays(7);
        DateTime minus8 = DateTime.now().minusDays(8);
        DateTime minus9 = DateTime.now().minusDays(9);
        DateTime minus10 = DateTime.now().minusDays(10);
        DateTime minus11 = DateTime.now().minusDays(11);
        DateTime minus12 = DateTime.now().minusDays(12);
        DateTime minus13 = DateTime.now().minusDays(13);

        assertThat(sightingsFormatter.getDisplayString(minus7), is(String.format("Last %s", mapping.get(minus7.getDayOfWeek()))));
        assertThat(sightingsFormatter.getDisplayString(minus8), is(String.format("Last %s", mapping.get(minus8.getDayOfWeek()))));
        assertThat(sightingsFormatter.getDisplayString(minus9), is(String.format("Last %s", mapping.get(minus9.getDayOfWeek()))));
        assertThat(sightingsFormatter.getDisplayString(minus10), is(String.format("Last %s", mapping.get(minus10.getDayOfWeek()))));
        assertThat(sightingsFormatter.getDisplayString(minus11), is(String.format("Last %s", mapping.get(minus11.getDayOfWeek()))));
        assertThat(sightingsFormatter.getDisplayString(minus12), is(String.format("Last %s", mapping.get(minus12.getDayOfWeek()))));
        assertThat(sightingsFormatter.getDisplayString(minus13), is(String.format("Last %s", mapping.get(minus13.getDayOfWeek()))));
    }

    @Test
    public void shouldReturnDateIfOlderThanTwoWeeksAgo() {
        DateTime oldDate = DateTime.parse("2014-02-28");

        assertThat(sightingsFormatter.getDisplayString(oldDate), is("2014-02-28"));
    }
}
