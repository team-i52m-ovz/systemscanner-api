package com.systemscanner.api.controller;

import com.systemscanner.api.model.mongo.ReportInfo;
import com.systemscanner.api.service.ScannerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.function.Function;

import static com.systemscanner.api.utils.HttpProperties.HttpHeaders.SCANNER;
import static com.systemscanner.api.utils.HttpProperties.HttpHeaders.SCANNER_SECRET;

@RestController
@AllArgsConstructor
@RequestMapping("/scanner")
public class ScannerController {

	private final ScannerService scannerService;

	@PostMapping
	public ResponseEntity<?> registerInstance(@RequestHeader(SCANNER_SECRET) String secret) {
		Function<String, ResponseEntity<?>> setResponseToken = token ->
				ResponseEntity
						.noContent()
						.header(SCANNER, token)
						.build();

		return this.scannerService.registerInstance(secret)
				.map(setResponseToken)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@PostMapping("/report")
	public ResponseEntity<?> createReport(@RequestBody ReportInfo reportInfo,
										  @RequestHeader(SCANNER) String scannerAuthentication) {
		return this.scannerService.createReport(reportInfo, scannerAuthentication)
				.map(r -> ResponseEntity.accepted().build())
				.orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

}
