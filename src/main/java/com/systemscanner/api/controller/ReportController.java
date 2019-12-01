package com.systemscanner.api.controller;

import com.systemscanner.api.model.dto.ScannerInstanceKey;
import com.systemscanner.api.model.mongo.Report;
import com.systemscanner.api.model.projection.ReportLight;
import com.systemscanner.api.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {

	private final ReportService reportService;

	@PostMapping
	public Page<ReportLight> findAllForCurrentInstance(@Valid @RequestBody ScannerInstanceKey instanceKey,
													   Authentication authentication,
													   Pageable pageable) {
		return this.reportService.findAllForCurrentUser(instanceKey.getPid(), authentication.getName(), pageable);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Report> findById(@PathVariable("id") String id,
										   Authentication authentication) {
		return this.reportService.findById(id, authentication.getName())
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
}
