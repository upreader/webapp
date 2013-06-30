package com.upreader.js;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JavaScriptWriter {
	private static final int CIRCULAR_REFERENCE_MIN_DEPTH = 10;
	private static final JavaScriptWriter STANDARD = new JavaScriptWriter(new HashMap<Class, VisitorFactory>());

	private final Map<Class, VisitorFactory> visitorFactories;
	private final List<Map.Entry<Class, VisitorFactory>> visitorFactoryEntries;

	public static JavaScriptWriter standard() {
		return STANDARD;
	}

	public static Builder custom() {
		return new Builder();
	}

	private JavaScriptWriter(Map<Class, VisitorFactory> visitorFactories) {
		LinkedHashMap<Class, VisitorFactory> vf = new LinkedHashMap<>(visitorFactories);
		if (!vf.containsKey(Map.class)) {
			vf.put(Map.class, Visitors.forMaps());
		}
		if (!vf.containsKey(Iterable.class)) {
			vf.put(Iterable.class, Visitors.forIterables());
		}
		if (!vf.containsKey(Iterator.class)) {
			vf.put(Iterator.class, Visitors.forIterators());
		}
		if (!vf.containsKey(Enumeration.class)) {
			vf.put(Enumeration.class, Visitors.forEnumerations());
		}

		this.visitorFactories = Collections.unmodifiableMap(vf);
		this.visitorFactoryEntries = Collections.unmodifiableList(new ArrayList(this.visitorFactories.entrySet()));
	}

	public String write(Object object) {
		StringBuilder out = new StringBuilder();
		try {
			write(object, out);
		} catch (IOException e) {
			throw new AssertionError(e);
		}
		return out.toString();
	}

	public void write(Object object, Appendable out) throws IOException {
		requireNonNull(out, "out");
		write(object, out, 0, null);
	}

	private <T> VisitorFactory<T> getVisitorFactory(T object) {
		if (object.getClass().isArray()) {
			return Visitors.forArrays();
		}

		VisitorFactory visitorFactory = this.visitorFactories.get(object.getClass());
		if (visitorFactory != null) {
			return visitorFactory;
		}

		if ((object instanceof JavaScriptObject)) {
			return ((JavaScriptObject) object).getJsVisitorFactory();
		}

		int i = 0;
		for (int size = this.visitorFactoryEntries.size(); i < size; i++) {
			Map.Entry entry = (Map.Entry) this.visitorFactoryEntries.get(i);
			if (((Class) entry.getKey()).isInstance(object)) {
				return (VisitorFactory) entry.getValue();
			}

		}

		return (VisitorFactory<T>) NullVisitorFactory.INSTANCE;
	}

	private void write(Object object, Appendable out, int depth, Set<Object> parents) throws IOException {
		if ((object == null) || ((object instanceof Byte)) || ((object instanceof Short)) || ((object instanceof Integer))
				|| ((object instanceof Long)) || ((object instanceof Float)) || ((object instanceof Double))
				|| ((object instanceof Boolean))) {
			out.append(String.valueOf(object));
			return;
		}

		if (((object instanceof String)) || ((object instanceof Character))) {
			stringify(object, out);
			return;
		}

		VisitorFactory visitorFactory = getVisitorFactory(object);
		if (visitorFactory == NullVisitorFactory.INSTANCE) {
			stringify(object, out);
			return;
		}

		Set localParents = parents;

		if (depth > 10) {
			if (localParents == null) {
				localParents = Collections.newSetFromMap(new IdentityHashMap());
			}
			if (!localParents.add(object)) {
				throw new IllegalArgumentException("Detected circular reference from object: " + object);
			}
		}

		Visitor visitor = visitorFactory.visitor(object);
		if (visitor.isArray()) {
			out.append('[');
			if (!visitor.hasNext()) {
				out.append(']');
				if (localParents != null) {
					localParents.remove(object);
				}
				return;
			}
			visitor.next();
			write(visitor.value(), out, depth + 1, localParents);
			while (visitor.hasNext()) {
				visitor.next();
				out.append(',');
				write(visitor.value(), out, depth + 1, localParents);
			}
			out.append(']');
			if (localParents != null) {
				localParents.remove(object);
			}
			return;
		}

		out.append('{');
		if (!visitor.hasNext()) {
			out.append('}');
			if (localParents != null) {
				localParents.remove(object);
			}
			return;
		}
		visitor.next();
		stringify(visitor.name(), out);
		out.append(':');
		write(visitor.value(), out, depth + 1, localParents);
		while (visitor.hasNext()) {
			visitor.next();
			out.append(',');
			stringify(visitor.name(), out);
			out.append(':');
			write(visitor.value(), out, depth + 1, localParents);
		}
		out.append('}');
		if (localParents != null) {
			localParents.remove(object);
		}
	}

	private static void requireNonNull(Object argument, String name) {
		if (argument == null) {
			throw new IllegalArgumentException("Argument \"" + name + "\" may not be null.");
		}
	}

	private static void stringify(Object object, Appendable out) throws IOException {
		String text = object.toString();
		out.append('"');
		int i = 0;
		for (int length = text.length(); i < length; i++) {
			char c = text.charAt(i);
			switch (c) {
			case '\\':
				out.append("\\\\");
				break;
			case '\t':
				out.append("\\t");
				break;
			case '\b':
				out.append("\\b");
				break;
			case '\n':
				out.append("\\n");
				break;
			case '\r':
				out.append("\\r");
				break;
			case '\f':
				out.append("\\f");
				break;
			case '"':
			case '&':
			case '\'':
			case '<':
			case '=':
			case '>':
			case ' ':
				out.append(String.format("\\u%04x", new Object[] { Integer.valueOf(c) }));
				break;
			default:
				if ((c >= 0) && (c <= '\037')) {
					out.append(String.format("\\u%04x", new Object[] { Integer.valueOf(c) }));
				} else {
					out.append(c);
				}
				break;
			}
		}
		out.append('"');
	}

	public static class Builder {
		private static final Set<Class> UNCUSTOMIZABLE_TYPES = Collections.unmodifiableSet(new HashSet(Arrays.asList(new Class[] {
				Boolean.TYPE, Byte.TYPE, Integer.TYPE, Short.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Void.TYPE, Boolean.class,
				Byte.class, Integer.class, Short.class, Long.class, Float.class, Double.class, Void.class, String.class })));

		private final LinkedHashMap<Class, VisitorFactory> visitorFactories = new LinkedHashMap<>();

		private static void requireCustomizableType(Class<?> type) {
			if ((type.isArray()) || (UNCUSTOMIZABLE_TYPES.contains(type))) {
				throw new IllegalArgumentException("Rendering of type \"" + type + "\" cannot be customized.");
			}
		}

		public <T> Builder addVisitorFactory(Class<? extends T> type, VisitorFactory<? extends T> visitorFactory) {
			JavaScriptWriter.requireNonNull(type, "type");
			JavaScriptWriter.requireNonNull(visitorFactory, "visitorFactory");
			requireCustomizableType(type);
			this.visitorFactories.put(type, visitorFactory);
			return this;
		}

		public Builder renderAsStrings(Class type) {
			JavaScriptWriter.requireNonNull(type, "type");
			requireCustomizableType(type);
			this.visitorFactories.put(type, JavaScriptWriter.NullVisitorFactory.INSTANCE);
			return this;
		}

		public JavaScriptWriter build() {
			return this.visitorFactories.isEmpty() ? JavaScriptWriter.STANDARD : new JavaScriptWriter(this.visitorFactories);
		}
	}

	private static enum NullVisitorFactory implements VisitorFactory {
		INSTANCE;

		public Visitor visitor(Object object) {
			throw new UnsupportedOperationException();
		}
	}
}
