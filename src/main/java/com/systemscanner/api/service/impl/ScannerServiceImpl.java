package com.systemscanner.api.service.impl;

import com.systemscanner.api.model.entity.ScannerInstance;
import com.systemscanner.api.model.mongo.Report;
import com.systemscanner.api.model.mongo.ReportInfo;
import com.systemscanner.api.repository.jpa.ScannerInstanceRepository;
import com.systemscanner.api.repository.mongo.ReportMongoRepository;
import com.systemscanner.api.service.ScannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScannerServiceImpl implements ScannerService {

	private final ScannerAuthenticator scannerAuthenticator;
	private final ReportMongoRepository reportMongoRepository;
	private final ScannerInstanceRepository scannerInstanceRepository;

	@Value("${scanner.secret}")
	private String secret;

	@Override
	public Optional<String> registerInstance(String scannerSecret) {
		return Optional.of(scannerSecret)
				.filter(secret::equals)
				.map(authorized -> this.createInstance())
				.map(this.scannerInstanceRepository::save)
				.flatMap(this.scannerAuthenticator::authenticate);
	}

	@Override
	public Optional<Report> createReport(ReportInfo reportInfo, String scannerToken) {
		return this.scannerAuthenticator.getPidTokenPair(scannerToken)
				.flatMap(pidTokenPair -> scannerInstanceRepository.findByPidAndSecurityKey(pidTokenPair.getFirst(), pidTokenPair.getSecond()))
				.map(scanner -> this.buildReport(reportInfo, scanner))
				.map(reportMongoRepository::save);
	}

	private ScannerInstance createInstance() {
		return ScannerInstance.builder()
				.pid(UUID.randomUUID().toString())
				.securityKey(UUID.randomUUID().toString())
				.build();
	}

	private Report buildReport(ReportInfo reportInfo, ScannerInstance scanner) {
		return Report.builder()
				.scannerPid(scanner.getPid())
				.createdAt(Instant.now())
				.details(reportInfo)
				.build();
	}

}
