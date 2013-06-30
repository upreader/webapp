package com.upreader.mustache;

import java.util.HashMap;
import java.util.Map;

import com.upreader.Infrastructure;
import com.upreader.UpreaderApplication;
import com.upreader.UpreaderVersion;
import com.upreader.util.Configurable;
import com.upreader.util.ConfigurationProperties;

/**
 * Make the application-scoped objects availble for the Mustache template
 * 
 * @author Flavius
 *
 */
public class TemplateAppReferences implements Configurable {
	public final UpreaderApplication application;
	public final Map<Object, Object> env;
	public final Map<Object, String> path;

	public TemplateAppReferences(UpreaderApplication application) {
		this.application = application;

		this.env = new HashMap();
		this.path = new HashMap();
	}

	public void configure(ConfigurationProperties props) {
		UpreaderVersion version = this.application.getVersion();

		this.env.clear();
		this.env.put("version", version.getVersionString());

		this.path.clear();
		Infrastructure inf = this.application.getInfrastructure();
		this.path.put("imgDir", inf.getImageDirectory());
		this.path.put("cssDir", inf.getCssDirectory());
		this.path.put("jsDir", inf.getJavaScriptDirectory());
		this.path.put("serverName", inf.getServerName());
		this.path.put("relativePath", inf.getUrl());
		this.path.put("standardDomain", inf.getStandardDomain());
		this.path.put("secureDomain", inf.getSecureDomain());
	}
}
