package com.shoyuanime.animeAnthology.configuration.security;

import java.util.UUID;

public class SecurityConstants {
    public static final String SECRET = UUID.randomUUID().toString();
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";
}
