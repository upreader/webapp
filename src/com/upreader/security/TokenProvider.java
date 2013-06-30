package com.upreader.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * An encrypted token
 * 
 * @author Flavius
 */
public class TokenProvider {
	private int capacity;
	private int tokenSize;
	private LinkedList<byte[]> tokens;
	private Random random;

	public TokenProvider(int capacity, int tokenSize) {
		if (capacity < 1) {
			throw new IllegalArgumentException("The capacity must be greater than zero.");
		}

		if (tokenSize < 1) {
			throw new IllegalArgumentException("The token size must be greater than zero.");
		}

		this.capacity = capacity;
		this.tokenSize = tokenSize;
		this.tokens = new LinkedList<>();
		try {
			this.random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			this.random = new Random();
		}
	}

	public synchronized byte[] next() {
		if (this.tokens.size() >= this.capacity) {
			this.tokens.removeLast();
		}
		byte[] token = new byte[this.tokenSize];
		this.random.nextBytes(token);
		this.tokens.addFirst(token);
		return token;
	}

	public synchronized boolean validate(byte[] token) {
		for (Iterator<byte[]> iter = this.tokens.iterator(); iter.hasNext();) {
			if (Arrays.equals(token, iter.next())) {
				iter.remove();
				return true;
			}
		}

		return false;
	}
}
