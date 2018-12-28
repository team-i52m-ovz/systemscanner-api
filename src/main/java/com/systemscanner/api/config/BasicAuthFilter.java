/*
package com.systemscanner.api.config;

import com.systemscanner.api.repository.ScannerInstanceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class BasicAuthFilter extends OncePerRequestFilter {
	private static final String BASIC_AUTH_PREFIX = "Basic ";
	private static final String SEMICOLON = ":";
	private static final String BLANK = "";

	private final ScannerInstanceRepository scannerInstanceRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
		val auth = request.getHeader(HttpHeaders.AUTHORIZATION);
		Optional.ofNullable(auth)
				.filter(header -> header.startsWith(BASIC_AUTH_PREFIX))
				.map(header -> header.replaceAll(BASIC_AUTH_PREFIX, BLANK))
				.ifPresent(header -> this.authenticateServiceCall(header, request, response, filterChain));
	}

	private void authenticateServiceCall(String header, HttpServletRequest request, HttpServletResponse response,
										 FilterChain filterChain) {
		val authorized = Optional.of(header)
				.map(Base64.getDecoder()::decode)
				.map(String::new)
				.map(h -> h.split(SEMICOLON))
				.filter(h -> h.length == 2)
				.flatMap(creds -> this.scannerInstanceRepository.findByPidAndSecurityKey(creds[0], creds[1]))
				.isPresent();

		try {
			if (authorized) {
				filterChain.doFilter(request, response);
			} else {
				handleUnauthorized(response);
			}
		} catch (ServletException | IOException ex) {
			log.error(ex.getMessage());
		}

	}

	private void handleUnauthorized(HttpServletResponse response) throws IOException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized API call");
	}
}
*/
