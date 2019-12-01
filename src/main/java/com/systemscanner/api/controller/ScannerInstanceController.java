package com.systemscanner.api.controller;

import com.systemscanner.api.model.dto.ScannerInstanceKey;
import com.systemscanner.api.model.projection.ScannerInstanceLight;
import com.systemscanner.api.service.ScannerInstanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

	@PostMapping
	public ResponseEntity<ScannerInstanceLight> findOne(@Valid @RequestBody ScannerInstanceKey scannerInstanceKey,
														Authentication authentication) {
		return this.scannerInstanceService.findOne(scannerInstanceKey.getPid(), authentication.getName())
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

}
