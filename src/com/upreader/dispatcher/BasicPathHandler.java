package com.upreader.dispatcher;

import java.util.Collections;
import java.util.Map;

import org.apache.log4j.Logger;

import com.upreader.UpreaderApplication;
import com.upreader.collection.ImmutableNamedValues;
import com.upreader.context.Attachments;
import com.upreader.context.Context;
import com.upreader.context.Cookies;
import com.upreader.context.TemplateContext;
import com.upreader.context.Headers;
import com.upreader.context.Messages;
import com.upreader.context.Query;
import com.upreader.context.SessionNamedValues;
import com.upreader.controller.ProjectController;
import com.upreader.controller.UserController;
import com.upreader.helper.JsonWriter;
import com.upreader.helper.StringHelper;
import com.upreader.helper.WebHelper;
import com.upreader.mustache.MustacheManager;

public abstract class BasicPathHandler {
	private Logger log = Logger.getLogger(BasicPathHandler.class);
	private final UpreaderApplication application;
	private final ThreadLocal<References> references;
	private final MustacheManager mustacheManager;
	private final JsonWriter javaScriptWriter;
	private final UserController userController;
	private final ProjectController projectController;
	
	private String baseUri;

	public BasicPathHandler(UpreaderApplication application, JsonWriter jsw) {
		this.application = application;
		this.references = new ThreadLocal<>();
		this.mustacheManager = application.getMustacheManager();
		this.javaScriptWriter = (jsw != null ? jsw : application.getJavaScriptWriter());
		this.userController = new UserController(this);
		this.projectController = new ProjectController(this);
	}

	public boolean prehandle(PathSegments segments, Context context) {
		this.references.set(new References(context, segments));
		return true;
	}

	public void posthandle(PathSegments segments, Context context) {
		this.references.remove();
	}

	public abstract boolean handle(PathSegments pathSegments, Context context);

	public boolean message(String message) {
		return render(Collections.singletonMap("message", message));
	}

	public boolean error(int httpStatusCode, String errorMessage) {
		return error(httpStatusCode, Collections.singletonMap("error", errorMessage));
	}

	public boolean error(int httpStatusCode, Map<String, String> response) {
		context().setStatus(httpStatusCode);
		return render(response);
	}

	public boolean badRequest(Map<String, String> response) {
		return error(400, response);
	}

	public boolean unauthorized(String errorMessage) {
		return error(401, errorMessage);
	}

	public boolean unauthorized(Map<String, String> response) {
		return error(401, response);
	}

	public boolean notFound(String errorMessage) {
		return error(404, errorMessage);
	}

	public boolean notFound(Map<String, String> response) {
		return error(404, response);
	}

	public boolean badHttpMethod(String errorMessage) {
		return error(405, errorMessage);
	}

	public boolean badHttpMethod(Map<String, String> response) {
		return error(405, response);
	}

	public boolean badRequest(String errorMessage) {
		return error(400, errorMessage);
	}

	public boolean redirect(String url) {
		return context().redirect(url);
	}

	public boolean redirectRelative(String uri) {
		return context().redirect(segments().getUriBelowOffset() + uri);
	}

	public boolean render(Object object) {
		String template = ((References) this.references.get()).template;

		if ((StringHelper.isEmpty(template)) || (this.mustacheManager == null) || (WebHelper.isJsonRequest(context()))) {
			return json(object);
		}

		return mustache(template, object);
	}

	public boolean render() {
		return render(context().templateContext().getMap());
	}

	public boolean json(Object object) {
		return WebHelper.sendJson(context(), object, this.javaScriptWriter);
	}

	public boolean text(String text) {
		return WebHelper.sendPlaintext(context(), text);
	}

	public boolean mustache(String template) {
		return mustache(template, context().templateContext().getMap());
	}

	public void setBaseUri(String uri) {
		this.baseUri = uri;
	}

	public boolean mustache(String template, Object object) {
		String filename = template + ".mustache";
		Context context = context();

		this.application.getDispatcher().renderStarting(context, template);
		try {
			return this.mustacheManager.render(filename, context, object);
		} finally {
			this.application.getDispatcher().renderComplete(context);
		}
	}

	public String getBaseUri() {
		return this.baseUri;
	}

	public Context context() {
		return this.references.get().context;
	}

	public PathSegments segments() {
		return this.references.get().segments;
	}

	public ImmutableNamedValues args() {
		return segments().getArguments();
	}

	public Query query() {
		return context().query();
	}

	public Messages messages() {
		return context().messages();
	}

	public SessionNamedValues session() {
		return context().session();
	}

	public TemplateContext delivery() {
		return context().templateContext();
	}

	public Cookies cookies() {
		return context().cookies();
	}

	public Headers headers() {
		return context().headers();
	}

	public Attachments files() {
		return context().files();
	}

	public String template() {
		return ((References) this.references.get()).template;
	}

	public BasicPathHandler template(String name) {
		((References) this.references.get()).template = name;
		return this;
	}

	public UpreaderApplication getApplication() {
		return application;
	}
	
	public String username() {
		return context().getUserName();
	}
	
	public UserController userController() {
		return this.userController;
	}
	
	public UserController projectController() {
		return this.userController;
	}
	
	public boolean isUserInRole(String roleName) {
		return context().isUserInRole(roleName);
	}
	
	public static class References {
		public final Context context;
		public final PathSegments segments;
		public String template;

		public References(Context context, PathSegments segments) {
			this.context = context;
			this.segments = segments;
		}
	}
}
