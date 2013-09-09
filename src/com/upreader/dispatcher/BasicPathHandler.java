package com.upreader.dispatcher;

import java.io.StringWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.upreader.controller.*;
import org.apache.log4j.Logger;

import com.upreader.UpreaderApplication;
import com.upreader.collection.ImmutableNamedValues;
import com.upreader.context.Attachments;
import com.upreader.context.Context;
import com.upreader.context.Cookies;
import com.upreader.context.Headers;
import com.upreader.context.Messages;
import com.upreader.context.Query;
import com.upreader.context.SessionNamedValues;
import com.upreader.context.TemplateContext;
import com.upreader.helper.JsonWriter;
import com.upreader.helper.ReflectionHelper;
import com.upreader.helper.StringHelper;
import com.upreader.helper.WebHelper;
import com.upreader.mustache.MustacheManager;
import com.upreader.util.Configurable;
import com.upreader.util.ConfigurationProperties;
import com.upreader.util.MethodAccess;

public abstract class BasicPathHandler implements Configurable {
	private Logger log = Logger.getLogger(BasicPathHandler.class);
	private final UpreaderApplication application;

	private final ThreadLocal<References> references;
	private final MustacheManager mustacheManager;
	private final JsonWriter javaScriptWriter;

	private static final Class<?>[] CONTEXT_PARAMETER = { Context.class };

	private final Map<String, PathSegmentMethod> annotatedHandleMethods;
	private final MethodAccess methodAccess;
	private final PathSegmentMethod defaultMethod;
	
	private String uploadFolder;
	
	private String baseUri;
	
	public BasicPathHandler(UpreaderApplication application) {
		this(application, null);
	}
	
	public BasicPathHandler(UpreaderApplication application, JsonWriter jsw) {
		this.application = application;
		this.references = new ThreadLocal<>();
		this.mustacheManager = application.getMustacheManager();
		this.javaScriptWriter = (jsw != null ? jsw : application.getJavaScriptWriter());
		this.annotatedHandleMethods = new HashMap<String, PathSegmentMethod>();
		this.methodAccess = MethodAccess.get(getClass());
		this.defaultMethod = discoverAnnotatedMethods();
		
		this.application.getConfigurator().addConfigurable(this);
	}
	
	@Override
	public void configure(ConfigurationProperties props) {
		this.uploadFolder = props.getProperty("UploadFolder");
	}
	
	public boolean prehandle(PathSegments segments, Context context) {
		this.references.set(new References(this, context, segments));
		return true;
	}

	public void posthandle(PathSegments segments, Context context) {
		this.references.remove();
	}

	public boolean message(String message) {
		return json(Collections.singletonMap("message", message));
	}

	public boolean error(int httpStatusCode, String errorMessage) {
		return error(httpStatusCode, Collections.singletonMap("error", errorMessage));
	}

	public boolean error(int httpStatusCode, Map<String, String> response) {
		context().setStatus(httpStatusCode);
		return json(response);
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

    public String serverError(String errorMessage) {
        context().setStatus(500);
        return errorMessage;
    }

	public boolean redirect(String url) {
		return context().redirect(url);
	}

	public boolean redirectRelative(String uri) {
		return context().redirect(segments().getUriBelowOffset() + uri);
	}

	public boolean json(Object object) {
		return WebHelper.sendJson(context(), object, this.javaScriptWriter);
	}

	public boolean text(String text) {
		return WebHelper.sendPlaintext(context(), text);
	}

	public boolean renderMustacheTemplate(String template, TemplateContext templateContext) {
		return renderMustacheTemplate(template, templateContext.getMap());
	}

	public void setBaseUri(String uri) {
		this.baseUri = uri;
	}

    /**
     * Render a Mustache template to output using standard TemplateReferences
     * together with the provided object
     *
     * @param template
     * @param object
     * @return
     */
	public boolean renderMustacheTemplate(String template, Object object) {
		String filename = template + ".mustache";
		Context context = context();

		this.application.getDispatcher().renderStarting(context, template);
		try {
			return this.mustacheManager.render(filename, context, object);
		} finally {
			this.application.getDispatcher().renderComplete(context);
		}
	}

    public String renderMustacheTemplateToString(String template, Object object) {
        String filename = template + ".mustache";
        Context context = context();
        StringWriter writer = new StringWriter();
        this.mustacheManager.render(filename, writer, context, object);
        return writer.toString();
    }

	public String getBaseUri() {
		return this.baseUri;
	}

	public Context context() {
		return this.references.get().context;
	}

    public UserController userController() {
        return this.references.get().userController;
    }

    public ProjectController projectController() {
        return this.references.get().projectController;
    }

    public WorkspaceController workspaceController() {
        return this.references.get().workspaceController;
    }

    public MonitoringBoardController monitoringBoardController() {
        return this.references.get().monitoringBoardController;
    }

    public LoginController loginController() {
        return this.references.get().loginController;
    }

	public String uploadFolder() {
		return uploadFolder;
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

	public Cookies cookies() {
		return context().cookies();
	}

	public Headers headers() {
		return context().headers();
	}

	public Attachments files() {
		return context().files();
	}

	public UpreaderApplication getApplication() {
		return application;
	}
	
	public String username() {
		return context().username();
	}
	
	public boolean isUserInRole(String roleName) {
		return context().isUserInRole(roleName);
	}
	
	private PathSegmentMethod discoverAnnotatedMethods() {
		Method[] methods = getClass().getDeclaredMethods();
		PathSegmentMethod toReturn = new PathSegmentMethod(-1, false);

		for (Method method : methods) {
			PathSegment ps = method.getAnnotation(PathSegment.class);
			if (ps != null) {
				String segment = StringHelper.isEmpty(ps.value()) ? method.getName() : ps.value();

				this.annotatedHandleMethods.put(segment, analyzeAnnotatedMethod(method));
			}

			PathDefault pd = method.getAnnotation(PathDefault.class);
			if (pd != null) {
				if (toReturn.index == -1) {
					toReturn = analyzeAnnotatedMethod(method);
				} else {
					throw new IllegalAccessError("More than one method has been marked as @PathDefault. See " + method.getName());
				}
			}
		}

		return toReturn;
	}

	protected PathSegmentMethod analyzeAnnotatedMethod(Method method) {
		if (Modifier.isPublic(method.getModifiers())) {
			if (method.getParameterTypes().length == 0) {
				return new PathSegmentMethod(this.methodAccess.getIndex(method.getName(), ReflectionHelper.NO_PARAMETERS), false);
			}
			if ((method.getParameterTypes().length == 1) && (method.getParameterTypes()[0] == Context.class)) {
				return new PathSegmentMethod(this.methodAccess.getIndex(method.getName(), CONTEXT_PARAMETER), true);
			}

			throw new IllegalAccessError("Method " + method.getName() + " must take zero arguments or a single Context argument.");
		}

		throw new IllegalAccessError("Method " + method.getName() + " must be public to be annotated as a @PathSegment or @PathDefault.");
	}

	public boolean handle(PathSegments segments, Context context) {
		return dispatchToAnnotatedMethod(getAnnotatedMethod(segments), context);
	}

	protected PathSegmentMethod getAnnotatedMethod(PathSegments segments) {
		// first try the full path
		String uri = segments.getUriAboveOffset(2).toString();
		if(uri.endsWith("/"))
			uri = uri.substring(0, uri.length()-1);
		PathSegmentMethod method = this.annotatedHandleMethods.get(uri);	
		return method != null ? method : this.defaultMethod;
	}

	protected boolean dispatchToAnnotatedMethod(PathSegmentMethod method, Context context) {
		if (method.index >= 0) {
			if (method.contextParameter) {
				return ((Boolean) this.methodAccess.invoke(this, method.index, new Object[] { context })).booleanValue();
			}

			return ((Boolean) this.methodAccess.invoke(this, method.index, ReflectionHelper.NO_VALUES)).booleanValue();
		}

		return false;
	}

	protected static class PathSegmentMethod {
		public final int index;
		public final boolean contextParameter;

		public PathSegmentMethod(int index, boolean contextParameter) {
			this.index = index;
			this.contextParameter = contextParameter;
		}
	}

    /**
     * Objects available in scope of a request
     */
	public static class References {
        public final BasicPathHandler handler;
		public final Context context;
		public final PathSegments segments;
        public final UserController userController;
        public final ProjectController projectController;
        public final WorkspaceController workspaceController;
        public final LoginController loginController;
        public final MonitoringBoardController monitoringBoardController;

		public References(BasicPathHandler handler, Context context, PathSegments segments) {
            this.handler = handler;
			this.context = context;
			this.segments = segments;
            this.userController = new UserController(handler, context);
            this.projectController = new ProjectController(handler, context);
            this.workspaceController = new WorkspaceController(handler, context);
            this.loginController = new LoginController(handler, context);
            this.monitoringBoardController = new MonitoringBoardController(handler, context);
		}
	}
}
