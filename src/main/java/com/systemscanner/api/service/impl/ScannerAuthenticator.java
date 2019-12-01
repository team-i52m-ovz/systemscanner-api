package com.systemscanner.api.service.impl;

import com.systemscanner.api.model.entity.ScannerInstance;
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

	public Optional<String> authenticate(ScannerInstance scannerInstance) {
		return Optional.of(scannerInstance)
				.map(instance -> String.format("%s:%s", instance.getPid(), instance.getSecurityKey()))
				.map(credentials -> Base64.getEncoder().encode(credentials.getBytes()))
				.map(String::new)
				.map(token -> String.format("%s%s", BASIC_AUTH_PREFIX, token));
	}

}
