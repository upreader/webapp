package com.upreader.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionHelper {
	public static String[] toStringArray(int[] intArray) {
		String[] toReturn = new String[intArray.length];
		for (int i = 0; i < toReturn.length; i++) {
			toReturn[i] = String.valueOf(intArray[i]);
		}
		return toReturn;
	}

	public static <E> boolean arrayContains(E[] array, E valueToFind) {
		return arrayIndexOf(array, valueToFind) >= 0;
	}

	public static <E> int arrayIndexOf(E[] array, E valueToFind) {
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				if (array[i] == valueToFind) {
					return i;
				}
			}
		}

		return -1;
	}

	public static <E> boolean isNonEmpty(Collection<E> inputCollection) {
		if (inputCollection != null) {
			return inputCollection.size() > 0;
		}

		return false;
	}

	public static <E> boolean isEmpty(Collection<E> inputCollection) {
		if (inputCollection != null) {
			return inputCollection.size() == 0;
		}

		return true;
	}

	public static <E> boolean isNonEmpty(E[] inputArray) {
		if (inputArray != null) {
			return inputArray.length > 0;
		}

		return false;
	}

	public static <E> boolean isEmpty(E[] inputArray) {
		if (inputArray != null) {
			return inputArray.length == 0;
		}

		return true;
	}

	@SafeVarargs
	public static final <E> List<E> toList(E... objects) {
		List<E> list = new ArrayList<>(objects.length);
		Collections.addAll(list, objects);
		return list;
	}
	
	@SafeVarargs
	public static final List<Integer> toIntegerList(int... objects) {
		List<Integer> list = new ArrayList<>(objects.length);
		for(int obj : objects)
			list.add(new Integer(obj));
		return list;
	}
}
