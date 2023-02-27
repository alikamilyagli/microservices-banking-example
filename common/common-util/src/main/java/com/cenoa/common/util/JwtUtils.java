package com.cenoa.common.util;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public class JwtUtils {

    private static final String CLAIM_USER_UUID = "sub";
    private static final String CLAIM_USERNAME = "username";

    public static UUID getUserUuid(final Jwt jwt) {
        return UUID.fromString(jwt.getClaimAsString(CLAIM_USER_UUID));
    }

    public static String getUsername(final Jwt jwt) {
        return jwt.getClaimAsString(CLAIM_USERNAME);
    }

}
