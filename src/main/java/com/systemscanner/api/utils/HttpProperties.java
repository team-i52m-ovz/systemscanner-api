package com.systemscanner.api.utils;

public interface HttpProperties {

	interface HttpHeaders {

		String SCANNER = "X-Scanner";

		String SCANNER_SECRET = "X-Secret";

	}

	interface TokenUtils {

		String JWT_PREFIX = "Bearer ";

		String AUTHORITIES_KEY = "roles";

		String BASIC_AUTH_PREFIX = "Basic ";

		Integer BASIC_AUTH_PARTS = 2;

	}

	interface PublicEndpoints {

		String[] SWAGGER_ENDPOINTS = {
				"/v2/api-docs",
				"/swagger-resources",
				"/swagger-resources/**",
				"/configuration/ui",
				"/configuration/security",
				"/swagger-ui.html",
				"/webjars/**",
				"/**/public/**"
		};

		String SCANNER_ENDPOINTS = "/scanner/**";

		String AUTH_ENDPOINTS = "/auth/**";

	}

}
