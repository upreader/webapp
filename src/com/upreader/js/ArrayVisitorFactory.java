package com.upreader.js;

import java.lang.reflect.Array;

public enum ArrayVisitorFactory implements VisitorFactory {
	INSTANCE;

	public Visitor visitor(Object object) {
		if ((object == null) || (!object.getClass().isArray())) {
			throw new IllegalArgumentException("Argument is not an array.");
		}
		return new ArrayVisitor(object);
	}

	private static final class ArrayVisitor implements Visitor {
		private final Object array;
		private final int length;
		private int index = -1;

		private ArrayVisitor(Object array) {
			this.array = array;
			this.length = Array.getLength(array);
		}

		public boolean hasNext() {
			return this.index < this.length - 1;
		}

		public boolean isArray() {
			return true;
		}

		public void next() {
			this.index += 1;
		}

		public String name() {
			throw new UnsupportedOperationException();
		}

		public Object value() {
			return Array.get(this.array, this.index);
		}
	}
}
