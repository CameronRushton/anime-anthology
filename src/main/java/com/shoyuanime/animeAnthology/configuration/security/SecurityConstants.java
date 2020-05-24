package com.shoyuanime.animeAnthology.configuration.security;

public class SecurityConstants {
    public static final String SECRET = "laun7Jh6X6t2zwyAynqt6dfj9cwp4V2Tf8y4AsH4AcioIuc0zx5Hou35QjUiCQO";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";
}
