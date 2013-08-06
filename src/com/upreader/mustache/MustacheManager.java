package com.upreader.mustache;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.upreader.UpreaderApplication;
import com.upreader.configuration.ConfigurationError;
import com.upreader.context.Context;
import com.upreader.util.Configurable;
import com.upreader.util.ConfigurationProperties;

/**
 * Mustache manager used to render Mustache template files
 * 
 * @author Flavius
 * 
 */
public class MustacheManager implements Configurable {
	public static final String DEFAULT_MUSTACHE_EXTENSION = ".renderMustacheTemplate";
	private final UpreaderApplication application;
	private final TemplateAppReferences applicationReferences;
	private MustacheFactory mustacheFactory;
	private String mustacheDirectory;
	private boolean enabled;
	private boolean developmentMode;

	public MustacheManager(UpreaderApplication application) {
		this.application = application;
		this.application.getConfigurator().addConfigurable(this);
		this.applicationReferences = new TemplateAppReferences(this.application);
		application.getConfigurator().addConfigurable(this.applicationReferences);
	}

	@Override
	public void configure(ConfigurationProperties props) {
		this.enabled = props.getYesNoProperty("Mustache.Enabled", true);
		this.mustacheDirectory = props.getProperty("MustacheDirectory", "${Servlet.WebInf}/renderMustacheTemplate/");
		this.developmentMode = props.getYesNoProperty("Mustache.DevMode", true);

		if (this.enabled) {
			File directory = new File(this.mustacheDirectory);

			if (!directory.isDirectory()) {
				throw new ConfigurationError("MustacheDirectory " + this.mustacheDirectory + " does not exist.");
			}
			this.mustacheFactory = new DefaultMustacheFactory(new File(this.mustacheDirectory));
		}
	}

	public TemplateReferences getTemplateReferences(Context context, Object data) {
		return new TemplateReferences(context, this.applicationReferences, data);
	}

	protected TemplateAppReferences getApplicationReferences() {
		return this.applicationReferences;
	}

	protected MustacheFactory getMustacheFactory() {
		return this.developmentMode ? new DefaultMustacheFactory(new File(this.mustacheDirectory)) : this.mustacheFactory;
	}

	public void render(String filename, Writer writer, Object[] data) {
		if ((filename == null) || (writer == null) || (data == null)) {
			throw new IllegalArgumentException("MustacheManager.render received at least one null argument: " + filename + " " + writer
					+ " " + data);
		}

		Mustache mustache = getMustacheFactory().compile(filename);
		mustache.execute(writer, data);
	}

	public boolean render(String filename, Context context, Object scope) {
		try {
			Writer writer = context.getWriter();
			TemplateReferences refs = getTemplateReferences(context, scope);
			render(filename, writer, new Object[] { refs });
			return true;
		} catch (IOException ioexc) {
		}
		return false;
	}

    public void  render(String filename, Writer writer, Context context, Object scope) {
        TemplateReferences refs = getTemplateReferences(context, scope);
        render(filename, writer, new Object[] { refs });
    }
}
