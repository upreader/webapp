package com.upreader.js;

public class Visitors {
	public static VisitorFactory forArrays() {
		return ArrayVisitorFactory.INSTANCE;
	}

	public static VisitorFactory forEnumerations() {
		return EnumerationVisitorFactory.INSTANCE;
	}

	public static VisitorFactory forIterators() {
		return IteratorVisitorFactory.INSTANCE;
	}

	public static VisitorFactory forIterables() {
		return IterableVisitorFactory.INSTANCE;
	}

	public static VisitorFactory forMaps() {
		return MapVisitorFactory.INSTANCE;
	}

	public static <T> VisitorFactory<T> forClass(Class<T> clazz, String[] namesAndMethods) {
		return new ReflectiveVisitorFactory(clazz, namesAndMethods);
	}

	public static Visitor map(Object[] namesAndValues) {
		if (namesAndValues == null) {
			throw new IllegalArgumentException("Argument 'namesAndValues' must not be null.");
		}
		if (namesAndValues.length % 2 != 0) {
			throw new IllegalArgumentException("Argument 'namesAndValues' must have an even number of elements.");
		}
		return new ArrayBackedMapVisitor(namesAndValues);
	}

	private static final class ArrayBackedMapVisitor implements Visitor {
		private final Object[] namesAndValues;
		private int index = -2;

		ArrayBackedMapVisitor(Object[] namesAndValues) {
			this.namesAndValues = namesAndValues;
		}

		public boolean hasNext() {
			return this.index < this.namesAndValues.length - 2;
		}

		public boolean isArray() {
			return false;
		}

		public String name() {
			if (this.index < 0) {
				throw new IllegalStateException("name() called before next()");
			}
			return String.valueOf(this.namesAndValues[this.index]);
		}

		public void next() {
			if (!hasNext()) {
				throw new IllegalStateException("next() called when hasNext() was false");
			}
			this.index += 2;
		}

		public Object value() {
			if (this.index < 0) {
				throw new IllegalStateException("value() called before next()");
			}
			return this.namesAndValues[(this.index + 1)];
		}
	}
}
