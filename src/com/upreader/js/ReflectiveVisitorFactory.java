package com.upreader.js;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReflectiveVisitorFactory implements VisitorFactory {
	private final List<String> names;
	private final List<Method> methods;

	public ReflectiveVisitorFactory(Class clazz, String[] namesAndMethods) {
		int size = namesAndMethods.length / 2;
		List newNames = new ArrayList(size);
		List newMethods = new ArrayList(size);
		for (int i = 0; i < size; i++) {
			newNames.add(namesAndMethods[(2 * i)]);
			String method = namesAndMethods[(2 * i + 1)];
			try {
				newMethods.add(clazz.getMethod(method, new Class[0]));
			} catch (NoSuchMethodException e) {
				throw new IllegalArgumentException("NoSuchMethodException for class " + clazz.getName() + " and method " + method + ".", e);
			} catch (SecurityException e) {
				throw new IllegalArgumentException("SecurityException for class " + clazz.getName() + " and method " + method + ".", e);
			}
		}
		this.names = Collections.unmodifiableList(newNames);
		this.methods = Collections.unmodifiableList(newMethods);
	}

	public Visitor visitor(Object object) {
		return new ReflectiveVisitor(object);
	}

	final class ReflectiveVisitor implements Visitor {
		private final Object object;
		private int index = -1;

		private ReflectiveVisitor(Object object) {
			this.object = object;
		}

		public boolean hasNext() {
			return this.index < ReflectiveVisitorFactory.this.names.size() - 1;
		}

		public boolean isArray() {
			return false;
		}

		public String name() {
			return (String) ReflectiveVisitorFactory.this.names.get(this.index);
		}

		public void next() {
			this.index += 1;
		}

		public Object value() {
			try {
				return ((Method) ReflectiveVisitorFactory.this.methods.get(this.index)).invoke(this.object, new Object[0]);
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new JavaScriptError("ReflectiveVisitor could not access method.", e);
			}
		}
	}
}
