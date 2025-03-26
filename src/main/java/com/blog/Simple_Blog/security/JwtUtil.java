package com.blog.Simple_Blog.security;

import com.blog.Simple_Blog.model.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long expirationTime;

    // Constructor injection (Secret key & Expiration from application.properties)
    public JwtUtil(
            @Value("${application.security.jwt.secret-key}") String secret,
            @Value("${application.security.jwt.expiration}") String expiration) {

        // Debugging logs
        System.out.println("Loaded JWT Secret Key from Properties");
        System.out.println("Loaded JWT Expiration Time: " + expiration);

        // Convert Base64 secret to a SecretKey
        try {
            byte[] keyBytes = Decoders.BASE64.decode(secret);
            this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Invalid JWT secret key format. Ensure it's Base64-encoded.", e);
        }

        // Convert expiration time to long
        try {
            this.expirationTime = Long.parseLong(expiration);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid expiration time format. Must be a number in milliseconds.", e);
        }
    }

    // Generate JWT Token for User and Role
    public String generateToken(String username, Set<Role> roles) {
        Set<String> roleStrings = roles.stream()
                .map(Enum::name)
                .collect(Collectors.toSet());

        return Jwts.builder()
                .subject(username)
                .claim("roles", roleStrings)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Set<Role> extractRoles(String token) {
        Claims claims = extractClaim(token, claimsData -> claimsData);

        Object rolesObject = claims.get("roles");
        System.out.println("Raw Roles from JWT: " + rolesObject);

        // Convert List<String> -> Set<Role>
        Set<String> roleStrings = new HashSet<>((List<String>) rolesObject);
        return roleStrings.stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = parseClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean validateToken(String token, String username) {
        try {
            return extractUsername(token).equals(username) && !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("🚨 Invalid JWT Token: " + e.getMessage());
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new JwtException("Token Expired: " + e.getMessage());
        } catch (MalformedJwtException e) {
            throw new JwtException("Malformed Token: " + e.getMessage());
        } catch (SignatureException e) {
            throw new JwtException("Invalid Signature: " + e.getMessage());
        } catch (Exception e) {
            throw new JwtException("JWT Parsing Error: " + e.getMessage());
        }
    }
}
