package com.upreader.context;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.upreader.UpreaderSession;
import com.upreader.collection.MutableNamedValues;
import com.upreader.helper.NumberHelper;

/**
 * Easy attribute handler for the HTTP Session
 * 
 * @author Flavius
 * 
 */
public class SessionNamedValues implements MutableNamedValues {
	private final Context context;

	public SessionNamedValues(Context context) {
		this.context = context;
	}

	@Override
	public boolean has(String name) {
		UpreaderSession currentSession = this.context.getSession(false);
		return (currentSession != null) && (currentSession.getAttribute(name) != null);
	}

	public <O> O getObject(String name, O defaultValue) {
		UpreaderSession currentSession = this.context.getSession(false);
		Object obj = currentSession != null ? currentSession.getAttribute(name) : null;
		return obj != null ? (O) obj : defaultValue;
	}

	public <O> O getObject(String name) {
		return getObject(name, null);
	}

	public SessionNamedValues putObject(String name, Object value) {
		this.context.getSession(true).setAttribute(name, value);

		return this;
	}

	@Override
	public String get(String name) {
		return get(name, null);
	}

	@Override
	public String get(String name, String defaultValue) {
		UpreaderSession session = this.context.getSession(false);
		if (session != null) {
			try {
				String value = (String) session.getAttribute(name);
				if (value != null) {
					return value;
				}

			} catch (ClassCastException localClassCastException) {
			}

		}

		return defaultValue;
	}

	@Override
	public int getInt(String name) {
		return getInt(name, 0);
	}

	@Override
	public int getInt(String name, int defaultValue) {
		UpreaderSession session = this.context.getSession(false);
		if (session != null) {
			try {
				Integer value = (Integer) session.getAttribute(name);
				if (value != null) {
					return value.intValue();
				}

			} catch (ClassCastException localClassCastException) {
			}

		}

		return defaultValue;
	}

	public int getInt(String name, int defaultValue, int minimum, int maximum) {
		return NumberHelper.boundInteger(getInt(name, defaultValue), minimum, maximum);
	}

	public long getLong(String name, long defaultValue, long minimum, long maximum) {
		return NumberHelper.boundLong(getLong(name, defaultValue), minimum, maximum);
	}

	@Override
	public long getLong(String name) {
		return getLong(name, 0L);
	}

	@Override
	public long getLong(String name, long defaultValue) {
		UpreaderSession session = this.context.getSession(false);
		if (session != null) {
			try {
				Long value = (Long) session.getAttribute(name);
				if (value != null) {
					return value.longValue();
				}

			} catch (ClassCastException localClassCastException) {
			}

		}

		return defaultValue;
	}

	@Override
	public boolean getBoolean(String name) {
		return getBoolean(name, false);
	}

	@Override
	public boolean getBoolean(String name, boolean defaultValue) {
		UpreaderSession session = this.context.getSession(false);
		if (session != null) {
			try {
				Boolean value = (Boolean) session.getAttribute(name);
				if (value != null) {
					return value.booleanValue();
				}

			} catch (ClassCastException localClassCastException) {
			}

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
		UpreaderSession session = this.context.getSession(false);
		if (session != null) {
			session.removeAttribute(name);
		}
		return this;
	}

	@Override
	public MutableNamedValues clear() {
		UpreaderSession session = this.context.getSession(false);
		if (session != null) {
			Enumeration<String> names = session.getAttributeNames();
			List<String> toRemove = new ArrayList<>();
			while (names.hasMoreElements()) {
				toRemove.add(names.nextElement());
			}

			for (String name : toRemove) {
				session.removeAttribute(name);
			}
		}
		return this;
	}
}
