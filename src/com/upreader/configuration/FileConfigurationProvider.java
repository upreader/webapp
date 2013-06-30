package com.upreader.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.upreader.Configurator;
import com.upreader.UpreaderApplication;
import com.upreader.util.ConfigurationProperties;

/**
 * Loads configuration properties from a file
 * 
 * @author Flavius
 * 
 */
public class FileConfigurationProvider implements ConfigurationProvider {
	private Logger log = Logger.getLogger(FileConfigurationProvider.class);

	@Override
	public boolean load(UpreaderApplication application, ConfigurationProperties props) {
		try {
			String filenames = getConfigFilenames(application);
			StringTokenizer tokenizer = new StringTokenizer(filenames, ",");
			while (tokenizer.hasMoreTokens()) {
				String filename = tokenizer.nextToken().trim();
				loadPropertiesFromFile(application, props, filename);
			}

			return props.size() > 0;
		} catch (IOException ioexc) {
			log.debug("IOException while loading configuration files: " + ioexc);
		}

		return false;
	}

	protected String getConfigFilenames(UpreaderApplication application) {
		String filenames;

		if (application.getServletConfigParameter("ConfigurationFile") != null) {
			filenames = application.getServletConfigParameter("ConfigurationFile");
			log.debug("Got configuration file from servlet config parameter: " + filenames);
		} else if (System.getProperty("ConfigurationFile") != null) {
			filenames = System.getProperty("ConfigurationFile");
			log.debug("Got configuration file from system property " + filenames);
		} else {
			String machineName = Configurator.getMachineName();
			filenames = machineName + ".conf";
			log.debug("Using default configuration file build from machine name: " + filenames);
		}

		return filenames;
	}

	protected void loadPropertiesFromFile(UpreaderApplication application, ConfigurationProperties props, String filename)
			throws IOException {
		boolean loaded = false;

		InputStream is = null;
		try {
			is = getClass().getClassLoader().getResourceAsStream(filename);
			if (is != null) {
				props.load(is);
				loaded = true;
			}
		} finally {
			if (is != null)
				is.close();
		}

		if (!loaded) {
			try {
				is = application.getResourceAsStream(filename);
				if (is != null) {
					props.load(is);
					loaded = true;
				}
			} finally {
				if (is != null)
					is.close();
			}
		}

		if (!loaded) {
			try {
				File file = new File(filename);
				if (file.exists()) {
					is = new FileInputStream(file);
					if (is != null) {
						props.load(is);
						loaded = true;
					}
				}
			} finally {
				if (is != null)
					is.close();
			}
		}

		if (!loaded) {
			try {
				File file = new File(new File(".").getCanonicalPath() + File.separator + filename);
				if (file.exists()) {
					is = new FileInputStream(file);
					if (is != null) {
						props.load(is);
						loaded = true;
					}
				}
			} finally {
				if (is != null)
					is.close();
			}
		}

		if (loaded) {
			log.debug("Read " + filename);
		}
	}
}
