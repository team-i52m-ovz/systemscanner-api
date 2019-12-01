package com.systemscanner.api.service.impl;

import com.systemscanner.api.model.entity.ScannerInstance;
import com.systemscanner.api.model.mongo.OperatingSystemInfo;
import com.systemscanner.api.model.mongo.Report;
import com.systemscanner.api.model.mongo.ReportInfo;
import com.systemscanner.api.model.mongo.os.Win32ComputerSystem;
import com.systemscanner.api.repository.jpa.ScannerInstanceRepository;
import com.systemscanner.api.repository.mongo.ReportMongoRepository;
import com.systemscanner.api.service.ScannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ScannerServiceImpl implements ScannerService {

	private final ScannerAuthenticator scannerAuthenticator;
	private final ReportMongoRepository reportMongoRepository;
	private final ScannerInstanceRepository scannerInstanceRepository;

	@Value("${scanner.secret}")
	private String secret;

	@Override
	public Optional<String> registerInstance(String scannerSecret, String remoteAddress) {
		return Optional.of(scannerSecret)
				.filter(secret::equals)
				.map(authorized -> this.createInstance(remoteAddress))
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

	private ScannerInstance createInstance(String remoteAddress) {
		return ScannerInstance.builder()
				.name(remoteAddress)
				.pid(UUID.randomUUID().toString())
				.securityKey(UUID.randomUUID().toString())
				.build();
	}

	private Report buildReport(ReportInfo reportInfo, ScannerInstance scanner) {
		Function<ReportInfo, String> extractName = r ->
				Optional.ofNullable(r.getOperatingSystemInfo())
						.map(OperatingSystemInfo::getWin32ComputerSystems)
						.map(Collection::stream)
						.flatMap(Stream::findAny)
						.map(Win32ComputerSystem::getName)
						.orElse(null);

		return Report.builder()
				.name(extractName.apply(reportInfo))
				.scannerPid(scanner.getPid())
				.createdAt(Instant.now())
				.details(reportInfo)
				.build();
	}

}
