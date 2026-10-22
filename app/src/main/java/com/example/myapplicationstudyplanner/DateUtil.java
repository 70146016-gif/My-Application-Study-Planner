package com.example.myapplicationstudyplanner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/** Small helpers for dates (works on all Android versions). */
public class DateUtil {

    private static final long ONE_DAY = 24L * 60 * 60 * 1000;

    /** Today at 00:00. */
    public static Calendar startOfToday() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c;
    }

    /** A date N days from today (negative = in the past). */
    public static long daysFromToday(int days) {
        Calendar c = startOfToday();
        c.add(Calendar.DAY_OF_YEAR, days);
        return c.getTimeInMillis();
    }

    /** A date picked in a DatePickerDialog (month starts at 0). */
    public static long dateOf(int year, int month, int day) {
        Calendar c = startOfToday();
        c.set(year, month, day);
        return c.getTimeInMillis();
    }

    /** e.g. 22 Oct 2026 */
    public static String format(long millis) {
        return new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date(millis));
    }

    /** Negative = past, 0 = today, 1 = tomorrow ... */
    public static long daysLeft(long millis) {
        return Math.round((millis - startOfToday().getTimeInMillis()) / (double) ONE_DAY);
    }

    public static String daysLeftText(long millis) {
        long n = daysLeft(millis);
        if (n < 0) return "Passed";
        if (n == 0) return "Today";
        if (n == 1) return "Tomorrow";
        return n + " days left";
    }
}
