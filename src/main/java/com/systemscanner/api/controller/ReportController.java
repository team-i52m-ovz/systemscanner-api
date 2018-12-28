package com.systemscanner.api.controller;

import com.systemscanner.api.model.dto.ScannerInstanceDTO;
import com.systemscanner.api.model.projection.ReportLight;
import com.systemscanner.api.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/reports")
public class ReportController {

	private final ReportService reportService;

	@PostMapping
	public Set<ReportLight> findAllInstances(@Valid @RequestBody ScannerInstanceDTO instance,
											 Authentication authentication) {
		return this.reportService.findAllForCurrentUser(instance.getPid(), authentication.getName());
	}
}
