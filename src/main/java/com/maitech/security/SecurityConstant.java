package com.maitech.security;

public class SecurityConstant {
    public static final String SECRET = "$2y$12$OCu861NixlpTdrF.7wJAouCy08QvEbevMDb/1F5QEXkWlUEyvA7DG";
    public static final long EXPIRATION_TIME = 86_400_000; // 864_000_00 = 01 Days && 864_000_000 = 10 Days
    public static final String TOKEN_PREFIX = "";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTH_LOGIN_URL = "/login";
}
