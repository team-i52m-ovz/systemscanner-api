package com.systemscanner.api.service.impl;

import com.systemscanner.api.model.dto.RatReportDTO;
import com.systemscanner.api.model.entity.Report;
import com.systemscanner.api.model.entity.ScannerInstance;
import com.systemscanner.api.repository.ReportRepository;
import com.systemscanner.api.repository.ScannerInstanceRepository;
import com.systemscanner.api.service.RatService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RatServiceImpl implements RatService {

	private final ScannerInstanceRepository scannerInstanceRepository;
	private final ReportRepository reportRepository;

	@Override
	public Report createReport(RatReportDTO ratReportDTO, String scannerPid, String scannerToken) {
		return scannerInstanceRepository.findByPidAndSecurityKey(scannerPid, scannerPid)
				.map(this::saveInternal)
				.orElseThrow(IllegalArgumentException::new);
	}

	// TODO: Implement properly when valid data is provided
	private Report saveInternal(ScannerInstance scannerInstance) {
		val report = Report.builder()
				.created(Instant.now())
				.scannerInstance(scannerInstance)
				.build();
		return this.reportRepository.saveAndFlush(report);
	}
}
