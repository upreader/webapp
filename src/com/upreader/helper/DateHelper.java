package com.upreader.helper;

import java.util.Calendar;

import com.upreader.collection.StringList;
import com.upreader.text.SynchronizedSimpleDateFormat;

public class DateHelper {
	public static final SynchronizedSimpleDateFormat STANDARD_SQL_FORMAT = new SynchronizedSimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	public static final SynchronizedSimpleDateFormat STANDARD_TECH_FORMAT = new SynchronizedSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final String[] HUMAN_DATE_LABELS = { "year", "week", "day", "hour", "minute", "second" };
	private static final long[] HUMAN_DATE_AMOUNTS = { 31536000000L, 604800000L, 86400000L, 3600000L, 60000L, 1000L };
	private static final Calendar CALENDAR_INSTANCE = Calendar.getInstance();
	
	public static String getHumanDifference(long millis, int specificity) {
		return getHumanDifference(millis, System.currentTimeMillis(), specificity);
	}

	public static String getHumanDifference(long millis1, long millis2, int specificity) {
		long difference = Math.abs(millis1 - millis2);

		return getHumanDuration(difference, specificity);
	}

	public static String getHumanDuration(long milliseconds, int specificity) {
		long ms = milliseconds;
		StringList toReturn = new StringList(", ");

		int level = 0;

		if (ms < 1000L) {
			return "Less than 1 second";
		}

		for (int i = 0; i < HUMAN_DATE_LABELS.length; i++) {
			int count = (int) (ms / HUMAN_DATE_AMOUNTS[i]);
			if (count > 0) {
				toReturn.add(count + " " + HUMAN_DATE_LABELS[i] + StringHelper.pluralize(count));
				level++;
				if (level == specificity) {
					return toReturn.toString();
				}

				ms -= count * HUMAN_DATE_AMOUNTS[i];
			}

		}

		return toReturn.toString();
	}

	public static Calendar getCalendarInstance() {
		return getCalendarInstance(System.currentTimeMillis());
	}

	public static Calendar getCalendarInstance(long datetime) {
		Calendar cal = (Calendar) CALENDAR_INSTANCE.clone();
		cal.setTimeInMillis(datetime);
		return cal;
	}
}
