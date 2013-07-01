package com.upreader.dispatcher;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.upreader.UpreaderApplication;
import com.upreader.context.Context;
import com.upreader.helper.ReflectionHelper;
import com.upreader.helper.StringHelper;
import com.upreader.js.JavaScriptWriter;
import com.upreader.util.MethodAccess;

public class MethodPathHandler extends BasicPathHandler {
	private Logger log = Logger.getLogger(MethodPathHandler.class);
	private static final Class<?>[] CONTEXT_PARAMETER = { Context.class };

	private final Map<String, PathSegmentMethod> annotatedHandleMethods;
	private final MethodAccess methodAccess;
	private final PathSegmentMethod defaultMethod;
	
	public MethodPathHandler(UpreaderApplication app) {
		this(app, null);
	}
	
	public MethodPathHandler(UpreaderApplication app, JavaScriptWriter jsw) {
		super(app, jsw);

		this.annotatedHandleMethods = new HashMap<String, MethodPathHandler.PathSegmentMethod>();
		this.methodAccess = MethodAccess.get(getClass());
		this.defaultMethod = discoverAnnotatedMethods();
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
		String uri = segments.getUriAboveOffset().toString();
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
}
