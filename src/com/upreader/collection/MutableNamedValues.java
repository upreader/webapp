package com.upreader.collection;

/**
 * Read-write contract for a name-value store
 * 
 * @author Flavius
 * 
 */
public interface MutableNamedValues extends ImmutableNamedValues {
	public MutableNamedValues put(String name, String value);
	public MutableNamedValues put(String name, int value);
	public MutableNamedValues put(String name, long value);
	public MutableNamedValues put(String name, boolean value);
	public MutableNamedValues remove(String name);
	public MutableNamedValues clear();
}
