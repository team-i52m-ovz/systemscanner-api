package com.systemscanner.api.service.impl;

import com.systemscanner.api.model.mongo.Report;
import com.systemscanner.api.model.projection.ReportLight;
import com.systemscanner.api.model.projection.ScannerInstanceLight;
import com.systemscanner.api.repository.jpa.ScannerInstanceRepository;
import com.systemscanner.api.repository.mongo.ReportMongoRepository;
import com.systemscanner.api.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

	private final ReportMongoRepository mongoRepository;
	private final ScannerInstanceRepository scannerInstanceRepository;

	@Override
	public Page<ReportLight> findAllForCurrentUser(String pid, String username, Pageable pageable) {
		return this.scannerInstanceRepository.findOneByUserAndPid(username, pid)
				.map(ScannerInstanceLight::getPid)
				.map(sci -> this.mongoRepository.findAllByScannerPid(sci, pageable))
				.orElse(Page.empty());
	}

	@Override
	public Optional<Report> findById(String id, String username) {
		Predicate<Report> authorized = report ->
				this.scannerInstanceRepository.findOneByUserAndPid(username, report.getScannerPid()).isPresent();

		return this.mongoRepository.findById(id)
				.filter(authorized);
	}

}
