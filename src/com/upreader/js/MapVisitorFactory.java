package com.upreader.js;

import java.util.Iterator;
import java.util.Map;

enum MapVisitorFactory implements VisitorFactory<Map> {
	INSTANCE;

	public Visitor visitor(Map object) {
		if (!(object instanceof Map)) {
			throw new IllegalArgumentException("Argument is not a map.");
		}
		return new MapEntryIteratorVisitor(object.entrySet().iterator());
	}

	private static final class MapEntryIteratorVisitor implements Visitor {
		private final Iterator<Map.Entry> iterator;
		private Map.Entry entry;

		private MapEntryIteratorVisitor(Iterator<Map.Entry> iterator) {
			this.iterator = iterator;
		}

		public boolean hasNext() {
			return this.iterator.hasNext();
		}

		public boolean isArray() {
			return false;
		}

		public String name() {
			return String.valueOf(this.entry.getKey());
		}

		public void next() {
			this.entry = this.iterator.next();
		}

		public Object value() {
			return this.entry.getValue();
		}
	}
}
