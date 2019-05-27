package com.systemscanner.api.service.impl;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Optional;

import static com.systemscanner.api.utils.HttpProperties.TokenUtils.BASIC_AUTH_PARTS;
import static com.systemscanner.api.utils.HttpProperties.TokenUtils.BASIC_AUTH_PREFIX;
import static com.systemscanner.api.utils.StringUtils.BLANK;
import static com.systemscanner.api.utils.StringUtils.COLON;

@Component
public class ScannerAuthenticator {

	public Optional<Pair<String, String>> getPidTokenPair(String ratToken) {
		return Optional.ofNullable(ratToken)
				.map(token -> token.replaceAll(BASIC_AUTH_PREFIX, BLANK))
				.map(token -> Base64.getDecoder().decode(token))
				.map(String::new)
				.map(token -> token.split(COLON))
				.filter(parts -> BASIC_AUTH_PARTS.equals(parts.length))
				.map(parts -> Pair.of(parts[0], parts[1]));
	}
}
