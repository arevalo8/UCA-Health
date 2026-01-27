package com.UCA_Health.backend.auth;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private final Key key;
	private final long ttlSeconds;

	public JwtService(
			@Value("${security.jwt.secret}") String secret,
			@Value("${security.jwt.ttlSeconds:3600}") long ttlSeconds
			) {

		this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.ttlSeconds = ttlSeconds;
	}

	public String generateToken(UserPrincipal principal) {
		Instant now = Instant.now();
		Instant exp = now.plusSeconds(ttlSeconds);

		return Jwts.builder()
				.setSubject(principal.getEmail())
				.addClaims(Map.of("uid", principal.getId()))
				.setIssuedAt(Date.from(now))
				.setExpiration(Date.from(exp))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

	public String extractSubject(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
}
