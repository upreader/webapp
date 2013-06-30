package com.upreader.helper;

import java.util.Iterator;

public class StringHelper {
	public static String padZero(int number, int digits) {
		return padArbitrary('0', Integer.toString(number), digits);
	}

	public static String padArbitrary(char pad, String string, int length) {
		if (string.length() >= length) {
			return string;
		}

		char[] buffer = new char[length];

		int padding = length - string.length();
		int position = 0;

		for (; position < padding; position++) {
			buffer[position] = pad;
		}

		System.arraycopy(string.toCharArray(), 0, buffer, position, string.length());

		return new String(buffer);
	}

	public static boolean equalsIgnoreCase(String s1, String s2) {
		if ((s1 != null) && (s2 != null)) {
			return s1.equalsIgnoreCase(s2);
		}

		return false;
	}

	public static boolean isNonEmpty(String inputString) {
		return (inputString != null) && (inputString.length() > 0);
	}

	public static boolean parseBoolean(String boolStr) {
		if (boolStr != null) {
			if ("true".equals(boolStr) || "yes".equals(boolStr))
				return true;
			else if ("false".equals(boolStr) || "no".equals(boolStr))
				return false;
		}

		throw new NumberFormatException("'" + boolStr + "' is not a valid boolean value.");
	}

	public static boolean isEmpty(String inputString) {
		return (inputString == null) || (inputString.length() < 1);
	}

	public static boolean isEmptyTrimmed(String inputString) {
		return (inputString == null) || (trim(inputString).length() == 0);
	}

	public static boolean isEmptyTrimmed(String[] inputStrings) {
		if ((inputStrings == null) || (inputStrings.length == 0)) {
			return true;
		}
		String[] arrayOfString = inputStrings;
		int j = inputStrings.length;
		for (int i = 0; i < j; i++) {
			String inputString = arrayOfString[i];

			if (isNonEmptyTrimmed(inputString)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNonEmptyTrimmed(String inputString) {
		return (inputString != null) && (trim(inputString).length() > 0);
	}

	public static boolean isNonEmptyTrimmed(String[] inputStrings) {
		if ((inputStrings == null) || (inputStrings.length == 0)) {
			return false;
		}
		String[] arrayOfString = inputStrings;
		int j = inputStrings.length;
		for (int i = 0; i < j; i++) {
			String inputString = arrayOfString[i];

			if (isEmptyTrimmed(inputString)) {
				return false;
			}
		}
		return true;
	}

	public static boolean parseBoolean(String boolStr, boolean defaultValue) {
		try {
			return parseBoolean(boolStr);
		} catch (NumberFormatException e) {
		}
		return defaultValue;
	}

	@SafeVarargs
	public static final <E> String join(String separator, E... values) {
		if ((values == null) || (values.length == 0)) {
			return "";
		}
		StringBuilder sb = new StringBuilder().append(values[0]);
		for (int i = 1; i < values.length; i++) {
			sb.append(separator).append(values[i]);
		}
		return sb.toString();
	}

	public static String join(String separator, Iterable<?> values) {
		if (values == null) {
			return "";
		}
		Iterator<?> iter = values.iterator();
		if (!iter.hasNext()) {
			return "";
		}
		StringBuilder sb = new StringBuilder().append(iter.next());
		while (iter.hasNext()) {
			sb.append(separator).append(iter.next());
		}
		return sb.toString();
	}

	public static String join(String separator, String[] values) {
		if ((values == null) || (values.length == 0)) {
			return "";
		}
		StringBuilder sb = new StringBuilder().append(values[0]);
		for (int i = 1; i < values.length; i++) {
			sb.append(separator).append(values[i]);
		}
		return sb.toString();
	}

	public static String join(String separator, char[] values) {
		if ((values == null) || (values.length == 0)) {
			return "";
		}
		StringBuilder sb = new StringBuilder().append(values[0]);
		for (int i = 1; i < values.length; i++) {
			sb.append(separator).append(values[i]);
		}
		return sb.toString();
	}

	public static String join(String separator, byte[] values) {
		if ((values == null) || (values.length == 0)) {
			return "";
		}
		StringBuilder sb = new StringBuilder().append(values[0]);
		for (int i = 1; i < values.length; i++) {
			sb.append(separator).append(values[i]);
		}
		return sb.toString();
	}

	public static String join(String separator, short[] values) {
		if ((values == null) || (values.length == 0)) {
			return "";
		}
		StringBuilder sb = new StringBuilder().append(values[0]);
		for (int i = 1; i < values.length; i++) {
			sb.append(separator).append(values[i]);
		}
		return sb.toString();
	}

	public static String join(String separator, int[] values) {
		if ((values == null) || (values.length == 0)) {
			return "";
		}
		StringBuilder sb = new StringBuilder().append(values[0]);
		for (int i = 1; i < values.length; i++) {
			sb.append(separator).append(values[i]);
		}
		return sb.toString();
	}

	public static String join(String separator, long[] values) {
		if ((values == null) || (values.length == 0)) {
			return "";
		}
		StringBuilder sb = new StringBuilder().append(values[0]);
		for (int i = 1; i < values.length; i++) {
			sb.append(separator).append(values[i]);
		}
		return sb.toString();
	}

	public static String join(String separator, float[] values) {
		if ((values == null) || (values.length == 0)) {
			return "";
		}
		StringBuilder sb = new StringBuilder().append(values[0]);
		for (int i = 1; i < values.length; i++) {
			sb.append(separator).append(values[i]);
		}
		return sb.toString();
	}

	public static String join(String separator, double[] values) {
		if ((values == null) || (values.length == 0)) {
			return "";
		}
		StringBuilder sb = new StringBuilder().append(values[0]);
		for (int i = 1; i < values.length; i++) {
			sb.append(separator).append(values[i]);
		}
		return sb.toString();
	}

	public static String join(String separator, boolean[] values) {
		if ((values == null) || (values.length == 0)) {
			return "";
		}
		StringBuilder sb = new StringBuilder().append(values[0]);
		for (int i = 1; i < values.length; i++) {
			sb.append(separator).append(values[i]);
		}
		return sb.toString();
	}

	public static boolean equals(String s1, String s2) {
		if ((s1 != null) && (s2 != null)) {
			return s1.equals(s2);
		}

		return false;
	}

	public static String trim(String s) {
		if (s != null) {
			return s.trim();
		}

		return "";
	}

	public static String[] trim(String[] s) {
		String[] toReturn = new String[0];

		if (CollectionHelper.isNonEmpty(s)) {
			toReturn = new String[s.length];
			for (int i = 0; i < s.length; i++) {
				toReturn[i] = trim(s[i]);
			}
		}

		return toReturn;
	}

	public static String replaceSubstrings(String source, String search, String replacement) {
		return replaceSubstrings(source, search, replacement, true);
	}

	public static String replaceSubstrings(String source, String search, String replacement, boolean ignoreCase) {
		if (ignoreCase) {
			return replaceSubstringsIgnoreCase(source, new String[] { search }, new String[] { replacement });
		}

		return replaceSubstrings(source, new String[] { search }, new String[] { replacement });
	}

	public static String replaceSubstrings(String source, String[] find, String[] replace) {
		return replaceSubstrings(source, source, find, replace);
	}

	public static String replaceSubstringsIgnoreCase(String source, String[] find, String[] replace) {
		if (source == null) {
			return "";
		}

		String uppercaseSearch = source.toUpperCase();

		String[] upperFind = new String[find.length];
		for (int i = 0; i < find.length; i++) {
			upperFind[i] = find[i].toUpperCase();
		}

		return replaceSubstrings(source, uppercaseSearch, upperFind, replace);
	}

	public static String replaceSubstrings(String source, String search, String[] find, String[] replace) {
		if (source == null) {
			return "";
		}
		if ((search == null) || (find == null) || (replace == null)) {
			return source;
		}

		StringBuilder buffer = new StringBuilder(source.length());

		int position = 0;

		while (position < source.length()) {
			int selection = -1;
			int foundAtPosition = -1;

			for (int findIndex = 0; (findIndex < find.length) && (findIndex < replace.length); findIndex++) {
				int currentFound = search.indexOf(find[findIndex], position);

				if ((currentFound >= 0) && ((currentFound < foundAtPosition) || (foundAtPosition == -1))) {
					foundAtPosition = currentFound;
					selection = findIndex;
				}

			}

			if (selection >= 0) {
				buffer.append(source.substring(position, foundAtPosition));
				buffer.append(replace[selection]);
				position = foundAtPosition + find[selection].length();
			} else {
				buffer.append(source.substring(position));
				position = source.length();
			}
		}

		return buffer.toString();
	}

	public static boolean containsNullSafe(String toSearch, String searchTerm) {
		if (isEmpty(toSearch)) {
			return false;
		}
		if (isEmpty(searchTerm)) {
			return true;
		}

		return toSearch.indexOf(searchTerm) > -1;
	}

	public static String pluralize(int value) {
		if (value != 1) {
			return "s";
		}

		return "";
	}

	public static String emptyDefault(String stringToTest, String defaultValue) {
		if (isEmpty(stringToTest)) {
			return defaultValue;
		}

		return stringToTest;
	}
}
