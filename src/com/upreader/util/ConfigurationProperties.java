package com.upreader.util;

import java.util.Properties;

import com.upreader.UpreaderApplication;
import com.upreader.helper.StringHelper;

/**
 * Upreader configuration properties provided by means of a file or other source
 * (ex. JMX)
 * 
 * @author Flavius
 * 
 */
public class ConfigurationProperties extends Properties {
	private transient UpreaderApplication application;

	public ConfigurationProperties(UpreaderApplication application) {
		setApplication(application);
	}

	public void setApplication(UpreaderApplication application) {
		this.application = application;
	}

	public UpreaderApplication getApplication() {
		return application;
	}

	public int getIntegerProperty(String name) {
		return getIntegerProperty(name, 0);
	}

	public int getIntegerProperty(String name, int defaultValue) {
		String value = getProperty(name);
		if (StringHelper.isNonEmpty(value)) {
			return NumberHelper.parseInt(value.trim(), defaultValue);
		}

		return defaultValue;
	}

	public boolean getYesNoProperty(String name) {
		return getYesNoProperty(name, false);
	}

	public boolean getYesNoProperty(String name, boolean defaultValue) {
		return StringHelper.parseBoolean(getProperty(name), defaultValue);
	}

	@Override
	public String getProperty(String name) {
		return macroExpand(super.getProperty(name));
	}

	public String getProperty(String key, String defaultValue) {
		return macroExpand(super.getProperty(key, defaultValue));
	}

	private String macroExpand(String value) {
		String expandedValue = value;

		if (expandedValue != null) {
			int markerLocation = expandedValue.indexOf("${");
			while (markerLocation >= 0) {
				int endLocation = expandedValue.indexOf("}", markerLocation);
				if (endLocation >= 0) {
					String macroName = expandedValue.substring(markerLocation + "${".length(), endLocation);

					String macroValue = getProperty(macroName, "");

					expandedValue = expandedValue.substring(0, markerLocation) + macroValue + expandedValue.substring(endLocation + 1);
				}

				markerLocation = expandedValue.indexOf("${");
			}
		}

		return expandedValue;
	}

	public boolean isDefined(String propertyName) {
		return getProperty(propertyName) != null;
	}

	public long getLongProperty(String name, long defaultValue) {
		long toReturn = defaultValue;
		try {
			String value = getProperty(name);
			if (value != null) {
				toReturn = Long.parseLong(value.trim());
			}

		} catch (NumberFormatException localNumberFormatException) {
		}

		return toReturn;
	}

	public long getLongProperty(String name, long defaultValue, long minimum, long maximum) {
		long toReturn = defaultValue;
		try {
			String value = getProperty(name);
			if (value != null) {
				toReturn = NumberHelper.boundLong(Long.parseLong(value.trim()), minimum, maximum);
			}

		} catch (NumberFormatException localNumberFormatException) {
		}

		return toReturn;
	}
}
