package com.systemscanner.api.controller;

import com.systemscanner.api.model.projection.ScannerInstanceLight;
import com.systemscanner.api.service.ScannerInstanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/scanner-instances")
public class ScannerInstanceController {

	private final ScannerInstanceService scannerInstanceService;

	@GetMapping
	public Set<ScannerInstanceLight> findAll(Authentication authentication) {
		return this.scannerInstanceService.findAllForCurrentUser(authentication.getName());
	}

}
