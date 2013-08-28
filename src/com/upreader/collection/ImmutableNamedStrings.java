package com.upreader.collection;

import java.util.HashMap;
import java.util.Map;

import com.upreader.helper.StringHelper;
import com.upreader.helper.NumberHelper;

public class ImmutableNamedStrings implements ImmutableNamedValues {
	private final Map<String, String> values;
	private boolean sealed;

	public ImmutableNamedStrings(Map<String, String> values) {
		this.values = values;
		this.sealed = true;
	}

	public ImmutableNamedStrings(int initialSize) {
		if (initialSize > 0) {
			this.values = new HashMap<>(initialSize);
			this.sealed = false;
		} else {
			this.values = null;
			this.sealed = true;
		}
	}

	public ImmutableNamedStrings seal() {
		this.sealed = true;
		return this;
	}

	public ImmutableNamedStrings put(String name, String value) {
		if (this.sealed) {
			throw new IllegalStateException("This BasicNamedValues is sealed; put is not permitted.");
		}

		this.values.put(name, value);
		return this;
	}

	public boolean has(String name) {
		return get(name) != null;
	}

	public String get(String name) {
		if (this.values == null) {
			return null;
		}

		return (String) this.values.get(name);
	}

	public String get(String name, String defaultValue) {
		String value = get(name);
		return value != null ? value : defaultValue;
	}

	public int getInt(String name) {
		return NumberHelper.parseInt(get(name), 0);
	}

	public int getInt(String name, int defaultValue) {
		return NumberHelper.parseInt(get(name), defaultValue);
	}

	public int getInt(String name, int defaultValue, int minimum, int maximum) {
		return NumberHelper.boundInteger(getInt(name, defaultValue), minimum, maximum);
	}

	public long getLong(String name, long defaultValue, long minimum, long maximum) {
		return NumberHelper.boundLong(getLong(name, defaultValue), minimum, maximum);
	}

	public long getLong(String name) {
		return getLong(name, 0L);
	}

	public long getLong(String name, long defaultValue) {
		return NumberHelper.parseLong(get(name), defaultValue);
	}

	public boolean getBoolean(String name) {
		return getBoolean(name, false);
	}

	public boolean getBoolean(String name, boolean defaultValue) {
		return StringHelper.parseBoolean(get(name), defaultValue);
	}
}
