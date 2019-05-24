package com.systemscanner.api.controller;

import com.systemscanner.api.model.dto.NewScannerInstance;
import com.systemscanner.api.model.projection.ScannerInstanceLight;
import com.systemscanner.api.service.ScannerInstanceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/scanner")
public class ScannerController {

	private final ScannerInstanceService scannerInstanceService;

	@GetMapping("/instances")
	public Set<ScannerInstanceLight> findAll(Authentication authentication) {
		return this.scannerInstanceService.findAllForCurrentUser(authentication.getName());
	}

	@PostMapping("/instances")
	public ResponseEntity<ScannerInstanceLight> addInstance(@Valid @RequestBody NewScannerInstance newScannerInstance,
															Authentication authentication) {
		return this.scannerInstanceService.createNewInstance(newScannerInstance, authentication.getName())
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
}
