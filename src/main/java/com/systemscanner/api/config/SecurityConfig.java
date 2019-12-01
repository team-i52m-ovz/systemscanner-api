package com.systemscanner.api.config;

import com.systemscanner.api.component.JwtBuilder;
import com.systemscanner.api.utils.HttpProperties;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import static com.systemscanner.api.utils.HttpProperties.PublicEndpoints.*;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpMethod.*;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtBuilder jwtBuilder;
	private final JwtAuthorizationFilter jwtAuthorizationFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.cors().configurationSource(corsConfigurationSource())
				.and()
				.csrf().disable()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(new RestAuthenticationEntryPoint())
				.and()
				.addFilterBefore(jwtAuthorizationFilter, JwtAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers(AUTH_ENDPOINTS, SCANNER_ENDPOINTS)
				.permitAll()
				.antMatchers(SWAGGER_ENDPOINTS)
				.permitAll()
				.anyRequest()
				.authenticated();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter(this.jwtBuilder);
		filter.setAuthenticationManager(authenticationManagerBean());
		filter.setFilterProcessesUrl("/auth/login");
		return filter;
	}


	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		val allowedHttpMethods = Stream.of(HEAD, GET, POST, PUT, DELETE, PATCH)
				.map(HttpMethod::toString)
				.collect(toList());

		configuration.setAllowedOrigins(Collections.singletonList("*"));
		configuration.setAllowedMethods(allowedHttpMethods);
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList(HttpHeaders.AUTHORIZATION, HttpHeaders.CACHE_CONTROL,
				HttpHeaders.CONTENT_TYPE, HttpProperties.HttpHeaders.SCANNER));
		configuration.addExposedHeader(HttpHeaders.AUTHORIZATION);
		configuration.addExposedHeader(HttpProperties.HttpHeaders.ROLES);

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
