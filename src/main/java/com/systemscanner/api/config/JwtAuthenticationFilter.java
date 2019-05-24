package com.systemscanner.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systemscanner.api.component.JwtBuilder;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final JwtBuilder jwtBuilder;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
												HttpServletResponse response) throws AuthenticationException {
		try {
			val principal = new ObjectMapper().readValue(request.getInputStream(), Credentials.class);

			return this.getAuthenticationManager()
					.authenticate(
							new UsernamePasswordAuthenticationToken(principal.getUsername(), principal.getPassword())
					);

		} catch (IOException ignored) {
			throw new AccessDeniedException("Invalid credentials");
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
											FilterChain chain, Authentication authentication) {
		response.setHeader(HttpHeaders.AUTHORIZATION, jwtBuilder.buildJWT(authentication));
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
											  AuthenticationException ex) throws IOException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid username or password");
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	private static class Credentials {

		private String username;

		private String password;
	}
}
