package com.upreader.locale;

import java.util.Locale;
import java.util.ResourceBundle;

import com.upreader.UpreaderApplication;
import com.upreader.context.Context;

/**
 * Handles string resources based on locale
 * 
 * @author Flavius
 *
 */
public class LocaleManager {
	private static final String DEFAULT_RESOURCE_BUNDLE_NAME = "com.upreader.i18n.UpreaderResources";
	
	public static final String DEFAULT_LANGUAGE_CODE = "en";
	public static final String DEFAULT_COUNTRY_CODE = "US";
	private static LocaleManager instance;

	private final UpreaderApplication application;
	private final Locale defaultLocale;
	private final ResourceBundle defaultResources;
	
	public LocaleManager(UpreaderApplication application) {
		this(application, null);
	}

	public LocaleManager(UpreaderApplication application, ResourceBundle defaultResources) {
		this.application = application;
		this.defaultLocale = new Locale(DEFAULT_LANGUAGE_CODE, DEFAULT_COUNTRY_CODE);

		if (defaultResources != null) {
			this.defaultResources = defaultResources;
		} else {
			this.defaultResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_BUNDLE_NAME, defaultLocale);
		}

		instance = this;
	}

	public ResourceBundle getDefaultResources() {
		return defaultResources;
	}

	public UpreaderApplication getApplication() {
		return application;
	}

	public Locale getDefaultLocale() {
		return defaultLocale;
	}

	public static LocaleManager getInstance() {
		return instance;
	}

	public void setLocale(Context context, String languageID, String countryID) {
		context.session().putObject("Upreader-Locale", new Locale(languageID, countryID));
	}

	public void setLocale(Context context, Locale locale) {
		context.session().putObject("Upreader-Locale", locale);
	}

	public Locale getLocale(Context context) {
		if (context != null) {
			Locale toReturn = getLocaleRaw(context);

			return toReturn != null ? toReturn : getDefaultLocale();
		}

		return getDefaultLocale();
	}

	public Locale getLocaleRaw(Context context) {
		return (Locale) context.session().getObject("Upreader-Locale");
	}

	public ResourceBundle getResources(Context context) {
		return getResources(DEFAULT_RESOURCE_BUNDLE_NAME, getLocale(context));
	}

	public ResourceBundle getResources(String bundleName, Locale locale) {
		return ResourceBundle.getBundle(bundleName, locale);
	}
}
