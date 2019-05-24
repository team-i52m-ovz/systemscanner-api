package com.systemscanner.api.controller;

import com.systemscanner.api.model.mongo.ReportInfo;
import com.systemscanner.api.service.RatService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.systemscanner.api.utils.HttpProperties.HttpHeaders.SCANNER;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/rat")
public class RatController {

	private final RatService ratService;

	@PostMapping
	public ResponseEntity<?> createReport(@RequestBody ReportInfo reportInfo,
										  @RequestHeader(SCANNER) String scannerAuthentication) {
		return this.ratService.createReport(reportInfo, scannerAuthentication)
				.map(r -> ResponseEntity.accepted().build())
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
}
