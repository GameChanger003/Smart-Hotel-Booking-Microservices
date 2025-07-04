//package com.cts.hotel.hotelusers.JWT;
//
//import java.util.Date;
//
//import org.springframework.stereotype.Component;
//
//import io.jsonwebtoken.security.Keys;
//import java.security.Key;
//
//import io.jsonwebtoken.*;
//import java.util.function.Function;
//
//import javax.crypto.SecretKey;
//
//@Component
//public class JwtUtil {
//
////	private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//	private final Key SECRET_KEY = Keys.hmacShaKeyFor("your-super-secret-key-which-is-very-long-and-secure".getBytes());
//	private final long EXPIRATION_TIME = 1000 * 60 * 60;
//
//	public String generateToken(String email, Long userId, String role) {
//	    return Jwts.builder()
//	            .subject(email) 
//	            .claim("userId", userId) 
//	            .claim("role", role) 
//	            .issuedAt(new Date())
//	            .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//	            .signWith(SECRET_KEY)
//	            .compact();
//	}
//
//
//	public String extractUsername(String token) {
//		return extractClaim(token, Claims::getSubject);
//	}
//
//	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//		Claims claims = extractAllClaims(token);
//		return claimsResolver.apply(claims);
//	}
//
//	public Boolean validateToken(String token) {
//		try {
//			Jws<Claims> claims = Jwts.parser().verifyWith((SecretKey) SECRET_KEY).build().parseSignedClaims(token);
//			return true;
//		} catch (JwtException e) {
//			return false; 
//		}
//	}
//
//	Claims extractAllClaims(String token) {
//		return Jwts.parser().verifyWith((SecretKey) SECRET_KEY).build().parseSignedClaims(token).getPayload();
//	}
//
//	public Long extractUserId(String token) {
//		return extractClaim(token, claims -> claims.get("userId", Long.class));
//	}
//
//	public String extractRole(String token) {
//		return extractClaim(token, claims -> claims.get("role", String.class)); 
//	}
//
//	public String extractEmail(String token) {
//		return extractClaim(token, Claims::getSubject); 
//	}
//
//}

package com.cts.hotel.hotelusers.JWT;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    // 256-bit key (32 ASCII chars) for HS256
    private static final String SECRET = "MyUltraSecureSecretKey1234567890";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    public String generateToken(String email, Long userId, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
			Jws<Claims> claims = Jwts.parser().verifyWith((SecretKey) SECRET_KEY).build().parseSignedClaims(token);

            return true;
        } catch (JwtException e) {
            System.err.println("JWT validation failed: " + e.getMessage());
            return false;
        }
    }

    public Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith((SecretKey) SECRET_KEY).build().parseSignedClaims(token).getPayload();

    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", Long.class));
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    public String extractEmail(String token) {
        return extractUsername(token);
    }
}

