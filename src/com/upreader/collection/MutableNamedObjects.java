package com.upreader.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * java.util.Map backed writable name-value store
 * 
 * @author Flavius
 *
 */
public class MutableNamedObjects implements MutableNamedValues {
	private final Map<String, Object> map;

	public MutableNamedObjects() {
		this.map = new HashMap<>();
	}

	public Map<String, Object> getMap() {
		return new HashMap<>(this.map);
	}

	public <O> O getObject(String name, O defaultValue) {
		Object obj = this.map.get(name);
		return obj != null ? (O) obj : defaultValue;
	}

	public <O> O getObject(String name) {
		return getObject(name, null);
	}

	public MutableNamedObjects putObject(String name, Object value) {
		this.map.put(name, value);
		return this;
	}
	
	@Override
	public boolean has(String name) {
		return this.map.containsKey(name);
	}

	@Override
	public String get(String name) {
		return get(name, null);
	}

	@Override
	public String get(String name, String defaultValue) {
		try {
			String value = (String) this.map.get(name);
			if (value != null) {
				return value;
			}

		} catch (ClassCastException localClassCastException) {
		}

		return defaultValue;
	}
	
	@Override
	public int getInt(String name) {
		return getInt(name, 0);
	}

	@Override
	public int getInt(String name, int defaultValue) {
		try {
			Integer value = (Integer) this.map.get(name);
			if (value != null) {
				return value.intValue();
			}

		} catch (ClassCastException localClassCastException) {
		}

		return defaultValue;
	}

	@Override
	public long getLong(String name) {
		return getLong(name, 0L);
	}

	@Override
	public long getLong(String name, long defaultValue) {
		try {
			Long value = (Long) this.map.get(name);
			if (value != null) {
				return value.longValue();
			}

		} catch (ClassCastException localClassCastException) {
		}

		return defaultValue;
	}

	@Override
	public boolean getBoolean(String name) {
		return getBoolean(name, false);
	}

	@Override
	public boolean getBoolean(String name, boolean defaultValue) {
		try {
			Boolean value = (Boolean) this.map.get(name);
			if (value != null) {
				return value.booleanValue();
			}

		} catch (ClassCastException localClassCastException) {
		}

		return defaultValue;
	}

	@Override
	public MutableNamedValues put(String name, String value) {
		return putObject(name, value);
	}

	@Override
	public MutableNamedValues put(String name, int value) {
		return putObject(name, Integer.valueOf(value));
	}

	@Override
	public MutableNamedValues put(String name, long value) {
		return putObject(name, Long.valueOf(value));
	}

	@Override
	public MutableNamedValues put(String name, boolean value) {
		return putObject(name, Boolean.valueOf(value));
	}

	@Override
	public MutableNamedValues remove(String name) {
		this.map.remove(name);
		return this;
	}

	@Override
	public MutableNamedValues clear() {
		this.map.clear();
		return this;
	}
}
