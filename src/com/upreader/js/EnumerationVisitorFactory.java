package com.upreader.js;

import java.util.Enumeration;

enum EnumerationVisitorFactory implements VisitorFactory<Enumeration> {
	INSTANCE;

	public Visitor visitor(Enumeration object) {
		if (!(object instanceof Enumeration)) {
			throw new IllegalArgumentException("Argument is not an Enumeration.");
		}
		return new EnumerationVisitor(object);
	}

	private static final class EnumerationVisitor<E> implements Visitor {
		private final Enumeration<E> enumeration;
		private E value;

		private EnumerationVisitor(Enumeration<E> enumeration) {
			this.enumeration = enumeration;
		}

		public boolean hasNext() {
			return this.enumeration.hasMoreElements();
		}

		public boolean isArray() {
			return true;
		}

		public String name() {
			throw new UnsupportedOperationException();
		}

		public void next() {
			this.value = this.enumeration.nextElement();
		}

		public Object value() {
			return this.value;
		}
	}
}