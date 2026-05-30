package com.sunilos.p4.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Utility class for generating and validating JSON Web Tokens (JWT).
 * <p>
 * Tokens are signed with HMAC-SHA256 using a 32-byte secret key and expire
 * after {@value #EXPIRY_MS} milliseconds (24 hours by default).
 * <p>
 * Token claims:
 * <ul>
 * <li>{@code sub}    — login (email) of the authenticated user</li>
 * <li>{@code userId} — primary key of the {@code UserBean}</li>
 * <li>{@code roleId} — role assigned to the user</li>
 * </ul>
 *
 * @author Sunil Sahu
 * @version 1.0
 */
public class JwtUtil {

    /** HMAC-SHA256 secret — must be at least 32 bytes. Change before production. */
    private static final SecretKey KEY =
            Keys.hmacShaKeyFor("ORS_JWT_SECRET_KEY_MUST_BE_32_BYTES!".getBytes());

    /** Token validity: 24 hours in milliseconds. */
    private static final long EXPIRY_MS = 24L * 60 * 60 * 1000;

    private JwtUtil() {}

    /**
     * Generates a signed JWT for the authenticated user.
     *
     * @param userId the user's primary key
     * @param login  the user's login (email)
     * @param roleId the user's role id
     * @return a compact, URL-safe JWT string
     */
    public static String generateToken(long userId, String login, long roleId) {
        Date now    = new Date();
        Date expiry = new Date(now.getTime() + EXPIRY_MS);

        return Jwts.builder()
                .subject(login)
                .claim("userId", userId)
                .claim("roleId", roleId)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(KEY)
                .compact();
    }

    /**
     * Parses and validates a JWT string.
     *
     * @param token the compact JWT string (without the {@code Bearer } prefix)
     * @return the verified {@link Claims} payload
     * @throws io.jsonwebtoken.JwtException if the token is invalid, tampered, or expired
     */
    public static Claims validateToken(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Extracts the {@code userId} claim from an already-validated set of claims.
     *
     * @param claims the verified JWT payload
     * @return the user's primary key
     */
    public static long getUserId(Claims claims) {
        return ((Number) claims.get("userId")).longValue();
    }

    /**
     * Extracts the {@code roleId} claim from an already-validated set of claims.
     *
     * @param claims the verified JWT payload
     * @return the user's role id
     */
    public static long getRoleId(Claims claims) {
        return ((Number) claims.get("roleId")).longValue();
    }

    /**
     * Extracts the login (email) from an already-validated set of claims.
     *
     * @param claims the verified JWT payload
     * @return the user's login
     */
    public static String getLogin(Claims claims) {
        return claims.getSubject();
    }
}
