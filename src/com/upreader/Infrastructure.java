package com.upreader;

import com.upreader.context.Context;
import com.upreader.util.Configurable;
import com.upreader.util.ConfigurationProperties;

/**
 * Location of application folders and files
 * 
 * @author Flavius
 *
 */
public class Infrastructure implements Configurable {
	public static final String DEFAULT_IMAGES_DIR = "/images/";
	public static final String DEFAULT_CSS_DIR = "/css/";
	public static final String DEFAULT_JAVASCRIPT_DIR = "/js/";
	public static final String DEFAULT_JSP_DIR = "/WEB-INF/jsp/";

	private final UpreaderApplication application;
	private String servletName = "/core/dis";
	private String imageFileDirectory = DEFAULT_IMAGES_DIR;
	private String cssFileDirectory = DEFAULT_CSS_DIR;
	private String jsFileDirectory = DEFAULT_JAVASCRIPT_DIR;
	private String jspFileDirectory = DEFAULT_JSP_DIR;
	private String serverName = "";
	private String standardDomain = "";
	private String secureDomain = "";

	public Infrastructure(UpreaderApplication application) {
		this.application = application;
		application.getConfigurator().addConfigurable(this);
	}

	@Override
	public void configure(ConfigurationProperties props) {
		this.serverName = props.getProperty("ServerName", this.serverName);
		this.standardDomain = props.getProperty("StandardDomain", this.standardDomain);
		this.secureDomain = props.getProperty("SecureDomain", this.secureDomain);
	}

	public String getUrl() {
		return getName();
	}

	public String getName() {
		return this.servletName;
	}

	public String getImageDirectory() {
		return this.imageFileDirectory;
	}

	public String getCssDirectory() {
		return cssFileDirectory;
	}

	public String getJavaScriptDirectory() {
		return jsFileDirectory;
	}

	public String getJspDirectory() {
		return this.jspFileDirectory;
	}

	public String getServerName() {
		return this.serverName;
	}

	public String getStandardDomain() {
		return this.standardDomain;
	}

	public String getStandardDomain(Context context) {
		return "http://" + context.headers().host();
	}

	public String getSecureDomain() {
		return this.secureDomain;
	}

	public UpreaderApplication getApplication() {
		return application;
	}
}
