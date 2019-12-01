package com.systemscanner.api.controller;

import com.systemscanner.api.model.dto.ReportRestFilter;
import com.systemscanner.api.model.mongo.Report;
import com.systemscanner.api.model.projection.ReportLight;
import com.systemscanner.api.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/reports")
public class ReportController {

	private final ReportService reportService;

	@PostMapping
	public Page<ReportLight> findAllForCurrentInstance(@Valid @RequestBody ReportRestFilter instance,
													   Authentication authentication,
													   Pageable pageable) {
		return this.reportService.findAllForCurrentUser(instance.getPid(), authentication.getName(), pageable);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Report> findById(@PathVariable("id") String id,
										   Authentication authentication) {
		return this.reportService.findById(id, authentication.getName())
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
}
