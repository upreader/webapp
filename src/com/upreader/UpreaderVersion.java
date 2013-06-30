package com.upreader;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;

import com.upreader.helper.DateHelper;
import com.upreader.helper.StringHelper;

public class UpreaderVersion {
	private String versionString;
	private String buildDate = null;

	public UpreaderVersion() {
		this.setVersionString(getMajorVersion() + "." + StringHelper.padZero(getMinorVersion(), 2));
	}

	public int getMajorVersion() {
		return 0;
	}

	public int getMinorVersion() {
		return 1;
	}

	public String getVersionString() {
		return versionString;
	}

	public void setVersionString(String versionString) {
		this.versionString = versionString;
	}

	public String getJavaVersion() {
		return System.getProperty("java.version") + " (" + System.getProperty("java.vendor") + ")" + " on "
				+ System.getProperty("os.name") + " " + System.getProperty("os.version");
	}

	public String getBuildDate() {
		if (this.buildDate == null) {
			this.buildDate = "Unknown";
			try {
				String name = getClass().getCanonicalName();
				URL location = getClass().getClassLoader().getResource(name.replace('.', '/') + ".class");
				File source = new File(location.toURI());

				if (source.exists()) {
					this.buildDate = DateHelper.STANDARD_TECH_FORMAT.format(new Date(source.lastModified()));
				}

			} catch (URISyntaxException localURISyntaxException) {
			} catch (IllegalArgumentException localIllegalArgumentException) {
			}

		}

		return this.buildDate;
	}
}
