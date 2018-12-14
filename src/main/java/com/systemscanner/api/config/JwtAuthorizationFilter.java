package com.systemscanner.api.config;

import com.systemscanner.api.component.JwtBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final JwtBuilder jwtBuilder;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
				.ifPresent(this::authenticate);

		filterChain.doFilter(request, response);
	}

	private void authenticate(String token) {
		try {
			SecurityContextHolder.getContext().setAuthentication(jwtBuilder.getAuthentication(token));
		} catch (Exception ex) {
			log.error("Unauthorized: {}", ex.getMessage());
		}
	}
}
