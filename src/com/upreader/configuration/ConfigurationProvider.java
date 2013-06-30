package com.upreader.configuration;

import com.upreader.UpreaderApplication;
import com.upreader.util.ConfigurationProperties;

/**
 * Loads configuration properties from various sources
 * 
 * @author Flavius
 */
public interface ConfigurationProvider {
	public abstract boolean load(UpreaderApplication application, ConfigurationProperties configurationProperties);
}
