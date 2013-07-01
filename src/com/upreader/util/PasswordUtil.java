package com.upreader.util;

import com.caucho.server.security.PasswordDigest;

public class PasswordUtil {
	public static String encryptPassword(String user, String password) {
		PasswordDigest digest = new PasswordDigest();
		digest.setAlgorithm("MD5");
		digest.setFormat("plain");
		digest.init(); 
		try {
			char[] encrypted = digest.getPasswordDigest(user, password.toCharArray());
			return new String(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(PasswordUtil.encryptPassword("admin","asdqwe123"));
	}
}
