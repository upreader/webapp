package com.upreader.js;

enum IterableVisitorFactory implements VisitorFactory<Iterable> {
	INSTANCE;

	public Visitor visitor(Iterable object) {
		if (!(object instanceof Iterable)) {
			throw new IllegalArgumentException("Argument is not an iterable.");
		}
		return new IteratorVisitorFactory.IteratorVisitor(object.iterator());
	}
}