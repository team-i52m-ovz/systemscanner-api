package com.systemscanner.api.controller;

import com.systemscanner.api.model.dto.ScannerInstanceBody;
import com.systemscanner.api.model.dto.ScannerInstanceKey;
import com.systemscanner.api.model.dto.ScannerInstancePatch;
import com.systemscanner.api.model.projection.DetailedScannerInstance;
import com.systemscanner.api.model.projection.DisplayUser;
import com.systemscanner.api.model.projection.ScannerInstanceLight;
import com.systemscanner.api.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

	private final AdminService adminService;

	@GetMapping("/users")
	public Set<DisplayUser> findAllUsers() {
		return this.adminService.findAllUsers();
	}

	@GetMapping("/scanner-instances")
	public Set<ScannerInstanceLight> findAllScannerInstances() {
		return this.adminService.findAll();
	}

	@PostMapping("/scanner-instances")
	public ResponseEntity<ScannerInstanceLight> addNewScannerInstance(@Valid @RequestBody ScannerInstanceBody scannerInstanceBody) {
		return this.adminService.createNewInstance(scannerInstanceBody)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.badRequest().build());
	}

	@PatchMapping("/scanner-instances")
	public ResponseEntity<ScannerInstanceLight> updateScannerInstance(@Valid @RequestBody ScannerInstancePatch scannerInstancePatch) {
		return this.adminService.updateInstance(scannerInstancePatch)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/scanner-instances")
	public ResponseEntity<?> deleteInstance(@Valid @RequestBody ScannerInstanceKey scannerInstanceKey) {
		this.adminService.deleteByPid(scannerInstanceKey.getPid());

		return ResponseEntity.noContent().build();
	}

	@PostMapping("/scanner-instances/details")
	public ResponseEntity<DetailedScannerInstance> findDetailedScannerInstance(@Valid @RequestBody ScannerInstanceKey scannerInstanceKey) {
		return this.adminService.findByPid(scannerInstanceKey.getPid())
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

}
