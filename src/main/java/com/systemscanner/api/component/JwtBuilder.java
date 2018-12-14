package com.systemscanner.api.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtBuilder {
	private static final String TOKEN_PREFIX = "Bearer ";
	private static final String AUTHORITIES_KEY = "roles";
	private static final String BLANK = "";
	private static final String COMMA_DELIMITER = ",";

	@Value("${api.token.max-age}")
	private Long tokenMaxAge;

	@Value("${api.token.secret}")
	private String tokenSecret;

	public String buildJWT(Authentication auth) {
		String authorities = auth.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(COMMA_DELIMITER));

		val issuedAt = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
		val jwt = Jwts.builder()
				.setSubject(auth.getName())
				.claim(AUTHORITIES_KEY, authorities)
				.setIssuedAt(Date.from(issuedAt.toInstant(ZoneOffset.UTC)))
				.setExpiration(Date.from(issuedAt.plusMinutes(tokenMaxAge).toInstant(ZoneOffset.UTC)))
				.signWith(SignatureAlgorithm.HS256, tokenSecret)
				.compact();
		return TOKEN_PREFIX + jwt;
	}

	public String getAuthorities(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(COMMA_DELIMITER));
	}

	public Authentication getAuthentication(String token) {
		val jwt = token.replace(TOKEN_PREFIX, BLANK);
		Claims claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(jwt).getBody();

		Collection<? extends GrantedAuthority> authorities =
				Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(COMMA_DELIMITER))
						.filter(s -> !s.equals(BLANK))
						.map(SimpleGrantedAuthority::new).collect(Collectors.toList());

		User principal = new User(claims.getSubject(), BLANK, Collections.emptyList());

		return new UsernamePasswordAuthenticationToken(principal, BLANK, authorities);
	}
}
