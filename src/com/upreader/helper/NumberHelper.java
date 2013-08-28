package com.upreader.helper;

public class NumberHelper {
	public static int parseInt(String string) {
		return parseInt(string, 0);
	}

	public static int boundInteger(int toBound, int minimum, int maximum) {
		int result = toBound;
		if (result < minimum) {
			result = minimum;
		}
		if (result > maximum) {
			result = maximum;
		}

		return result;
	}

	public static int parseInt(String string, int defaultValue) {
		if (string == null) {
			return defaultValue;
		}

		int num = 0;
		int sign = -1;
		int len = string.length();

		if (len == 0) {
			return defaultValue;
		}

		char ch = string.charAt(0);
		if (ch == '-') {
			if (len == 1) {
				return defaultValue;
			}
			sign = 1;
		} else {
			int d = ch - '0';

			if ((d < 0) || (d > 9)) {
				return defaultValue;
			}
			num = -d;
		}

		int max = (sign == -1) ? (Integer.MIN_VALUE + 1) : Integer.MIN_VALUE;
		int multmax = max / 10;
		int i = 1;
		while (i < len) {
			int d = string.charAt(i++) - '0';

			if ((d < 0) || (d > 9)) {
				return defaultValue;
			}

			if (num < multmax) {
				return defaultValue;
			}

			num *= 10;

			if (num < max + d) {
				return defaultValue;
			}

			num -= d;
		}

		return sign * num;
	}

	public static long parseLong(String string) {
		return parseLong(string, 0L);
	}

	public static long parseLong(String string, long defaultValue, long minimum, long maximum) {
		return boundLong(parseLong(string, defaultValue), minimum, maximum);
	}

	public static long parseLong(String string, long defaultValue) {
		if (string == null) {
			return defaultValue;
		}

		long num = 0L;
		int sign = -1;
		int len = string.length();

		if (len == 0) {
			return defaultValue;
		}

		char ch = string.charAt(0);
		if (ch == '-') {
			if (len == 1) {
				return defaultValue;
			}
			sign = 1;
		} else {
			int d = ch - '0';

			if ((d < 0) || (d > 9)) {
				return defaultValue;
			}
			num = -d;
		}

		long max = (sign == -1) ? (Long.MIN_VALUE + 1) : Long.MIN_VALUE;
		long multmax = max / 10L;
		int i = 1;
		while (i < len) {
			int d = string.charAt(i++) - '0';

			if ((d < 0) || (d > 9)) {
				return defaultValue;
			}

			if (num < multmax) {
				return defaultValue;
			}

			num *= 10L;

			if (num < max + d) {
				return defaultValue;
			}

			num -= d;
		}

		return sign * num;
	}

	public static long boundLong(long toBound, long minimum, long maximum) {
		long result = toBound;
		if (result < minimum) {
			result = minimum;
		}
		if (result > maximum) {
			result = maximum;
		}

		return result;
	}

	public static int parseIntPermissive(String numStr, int defaultValue) {
		if (numStr != null) {
			try {
				return (int) Double.parseDouble(numStr);
			} catch (NumberFormatException localNumberFormatException) {
			}
		}

		return defaultValue;
	}

	public static float parseFloat(String numStr, float defaultValue) {
		if (numStr != null) {
			try {
				return Float.parseFloat(numStr);
			} catch (NumberFormatException localNumberFormatException) {
			}
		}

		return defaultValue;
	}

    public static int compareFloats(float f1, float f2) {
        float dif = f1 - f2;
        if(dif < 0) return -1;
        else if(dif == 0) return 0;
        else if(dif > 0) return 1;
        return 0;
    }
}
