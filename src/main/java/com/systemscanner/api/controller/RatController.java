package com.systemscanner.api.controller;

import com.systemscanner.api.model.dto.RatReportDTO;
import com.systemscanner.api.service.RatService;
import com.systemscanner.api.utils.HttpProperties;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/rat")
public class RatController {

	private final RatService ratService;

	@PostMapping
	public ResponseEntity<?> createReport(@Param(HttpProperties.HttpHeaders.SCANNER_PID) String scannerPid,
										  @Param(HttpProperties.HttpHeaders.SCANNER_TOKEN) String scannerToken,
										  @Valid @RequestBody RatReportDTO ratReportDTO) {
		return Optional.ofNullable(scannerPid)
				.filter(f -> Objects.nonNull(scannerToken))
				.map(i -> {
					this.ratService.createReport(ratReportDTO, scannerPid, scannerToken);
					return ResponseEntity.noContent().build();
				})
				.orElse(ResponseEntity.badRequest().build());
	}
}
