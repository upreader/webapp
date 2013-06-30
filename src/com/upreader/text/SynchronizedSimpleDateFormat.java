package com.upreader.text;

import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SynchronizedSimpleDateFormat extends SimpleDateFormat {
	public SynchronizedSimpleDateFormat(String format) {
		super(format);
	}

	public SynchronizedSimpleDateFormat() {
		this("yyyy-MM-dd HH:mm:ss.SSS");
	}

	public String format(Date date, String defaultValueWhenNull) {
		if (date == null) {
			return defaultValueWhenNull;
		}

		return format(date);
	}

	public String format(Date date, TimeZone tz) {
		synchronized (this) {
			TimeZone origTimeZone = getTimeZone();

			if (origTimeZone == null) {
				origTimeZone = TimeZone.getDefault();
			}

			if (tz != null) {
				setTimeZone(tz);
			}

			String toReturn = super.format(date);

			if (tz != null) {
				setTimeZone(origTimeZone);
			}

			return toReturn;
		}
	}

	public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition pos) {
		return format(date, toAppendTo, pos, null);
	}

	public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition pos, TimeZone tz) {
		synchronized (this) {
			TimeZone origTimeZone = getTimeZone();

			if (origTimeZone == null) {
				origTimeZone = TimeZone.getDefault();
			}

			if (tz != null) {
				setTimeZone(tz);
			}

			StringBuffer toReturn = super.format(date, toAppendTo, pos);

			if (tz != null) {
				setTimeZone(origTimeZone);
			}

			return toReturn;
		}
	}

	public Date parse(String source, TimeZone tz) throws ParseException {
		ParsePosition pos = new ParsePosition(0);
		Date result = parse(source, pos, tz);

		if (pos.getIndex() == 0) {
			throw new ParseException("Unparseable date: \"" + source + "\"", pos.getErrorIndex());
		}

		return result;
	}

	public Date parse(String source) throws ParseException {
		return parse(source, new ParsePosition(0));
	}

	public Date parse(String source, ParsePosition pos) {
		return parse(source, pos, null);
	}

	public Date parse(String source, ParsePosition pos, TimeZone tz) {
		synchronized (this) {
			TimeZone origTimeZone = getTimeZone();

			if (origTimeZone == null) {
				origTimeZone = TimeZone.getDefault();
			}

			if (tz != null) {
				setTimeZone(tz);
			}

			Date toReturn = super.parse(source, pos);

			if (tz != null) {
				setTimeZone(origTimeZone);
			}

			return toReturn;
		}
	}

	public String toString() {
		return super.toPattern();
	}

}
