package com.upreader;

/**
 * Constants used throughout the platform
 */
public class UpreaderConstants {
    public static final String LOGIN_COOKIE_NAME = "upreaderAuthId";

    /**
     * User roles
     */
	public static final String ROLE_PROSPECTOR = "prospector";
	public static final String ROLE_UPREADER = "upreader";
	public static final String ROLE_AUTHOR = "author";
    public static final String ROLE_PUBLISHER = "publisher";
	public static final String ROLE_EDITOR = "editor";
	public static final String ROLE_ADMIN = "admin";

    /**
     * Keys for objects stored in HTTP session
     */
	public static final String SESSION_USER = "user";
	public static final String SESSION_NEWPROJECT = "_newproject_";
}

