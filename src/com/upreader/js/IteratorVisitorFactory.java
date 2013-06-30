package com.upreader.js;

import java.util.Iterator;

enum IteratorVisitorFactory implements VisitorFactory<Iterator> {
	INSTANCE;

	public Visitor visitor(Iterator object) {
		if (!(object instanceof Iterator)) {
			throw new IllegalArgumentException("Argument is not an iterator.");
		}
		return new IteratorVisitor(object);
	}

	static final class IteratorVisitor implements Visitor {
		private final Iterator iterator;
		private Object value;

		IteratorVisitor(Iterator iterator) {
			this.iterator = iterator;
		}

		public boolean hasNext() {
			return this.iterator.hasNext();
		}

		public boolean isArray() {
			return true;
		}

		public String name() {
			throw new UnsupportedOperationException();
		}

		public void next() {
			this.value = this.iterator.next();
		}

		public Object value() {
			return this.value;
		}
	}
}
