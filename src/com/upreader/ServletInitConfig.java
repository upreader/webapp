package com.upreader;

import java.io.InputStream;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * Wrapper for UpreaderServlet initialization parameters
 * 
 * @author Flavius
 */
public class ServletInitConfig {
	private final ServletConfig config;
	private final ServletContext context;

	private ServletInitConfig(ServletConfig config, ServletContext context) {
		this.config = config;
		this.context = context;
	}

	public static ServletInitConfig createHttpInitConfig(ServletConfig config, ServletContext context) {
		return new ServletInitConfig(config, context);
	}

	public String getInitParameter(String name) {
		if (this.config == null) {
			return null;
		}
		return this.config.getInitParameter(name);
	}

	public InputStream getResourceAsStream(String filename) {
		if (this.config == null) {
			return null;
		}
		if (this.config.getServletContext() != null) {
			return this.config.getServletContext().getResourceAsStream(filename);
		}

		return null;
	}

	public Enumeration<String> getInitParameterNames() {
		if (this.config == null) {
			return null;
		}
		return this.config.getInitParameterNames();
	}

	public Object getAttribute(String name) {
		if (this.config == null) {
			return null;
		}
		return this.config.getServletContext().getAttribute(name);
	}

	public Enumeration<String> getAttributeNames() {
		if (this.config == null) {
			return null;
		}
		return this.config.getServletContext().getAttributeNames();
	}

	public String getRealPath(String path) {
		if (this.config == null) {
			return null;
		}
		return this.config.getServletContext().getRealPath(path);
	}

	public String getMimeType(String file) {
		if (this.config == null) {
			return null;
		}
		return this.config.getServletContext().getMimeType(file);
	}

	public String getServerInfo() {
		if (this.config == null) {
			return null;
		}
		return this.config.getServletContext().getServerInfo();
	}
}
