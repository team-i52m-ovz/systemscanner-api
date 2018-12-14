package com.systemscanner.api.component;

import com.systemscanner.api.model.dto.ErrorMessage;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ErrorMessage handleDataIntegrityViolation(DataIntegrityViolationException ex) {
		return () -> String.format("Invalid form value: %s", ex.getMessage());
	}
}
