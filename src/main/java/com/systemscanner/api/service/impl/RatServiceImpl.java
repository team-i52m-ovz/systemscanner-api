package com.systemscanner.api.service.impl;

import com.systemscanner.api.model.entity.ScannerInstance;
import com.systemscanner.api.model.mongo.Report;
import com.systemscanner.api.model.mongo.ReportInfo;
import com.systemscanner.api.repository.jpa.ScannerInstanceRepository;
import com.systemscanner.api.repository.mongo.ReportMongoRepository;
import com.systemscanner.api.service.RatService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RatServiceImpl implements RatService {

	private final ScannerAuthenticator scannerAuthenticator;
	private final ReportMongoRepository reportMongoRepository;
	private final ScannerInstanceRepository scannerInstanceRepository;

	@Override
	public Optional<Report> createReport(ReportInfo reportInfo, String scannerToken) {
		return this.scannerAuthenticator.getPidTokenPair(scannerToken)
				.flatMap(pidTokenPair -> scannerInstanceRepository.findByPidAndSecurityKey(pidTokenPair.getFirst(), pidTokenPair.getSecond()))
				.map(scanner -> this.buildReport(reportInfo, scanner))
				.map(reportMongoRepository::save);
	}

	private Report buildReport(ReportInfo reportInfo, ScannerInstance scanner) {
		return Report.builder()
				.scannerPid(scanner.getPid())
				.createdAt(Instant.now())
				.details(reportInfo)
				.build();
	}
}
