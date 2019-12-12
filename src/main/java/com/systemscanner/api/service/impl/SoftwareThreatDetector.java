package com.systemscanner.api.service.impl;

import com.systemscanner.api.model.entity.ScannerInstance;
import com.systemscanner.api.model.entity.SoftwarePublisher;
import com.systemscanner.api.model.mongo.software.Software;
import com.systemscanner.api.model.projection.SoftwareReport;
import com.systemscanner.api.repository.jpa.ScannerInstanceRepository;
import com.systemscanner.api.repository.jpa.SoftwarePublisherRepository;
import com.systemscanner.api.repository.mongo.ReportMongoRepository;
import com.systemscanner.api.service.MailNotificationSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

@Slf4j
@Component
@RequiredArgsConstructor
public class SoftwareThreatDetector {

	private final MailNotificationSender mailSender;
	private final ReportMongoRepository reportMongoRepository;
	private final ScannerInstanceRepository scannerInstanceRepository;
	private final SoftwarePublisherRepository softwarePublisherRepository;

	@Value("${scanner.software.blacklisted-publishers}")
	private Set<String> blackListedPublishers;

	@Value("${scanner.software.max-risk-factor}")
	private BigDecimal maxRiskFactor;

	@Scheduled(cron = "0 0 9 * * *")
	public void analyze() {
		val riskyPublishers = this.softwarePublisherRepository.findAllByRiskFactorGreaterThan(this.maxRiskFactor);

		this.scannerInstanceRepository.findAll()
				.parallelStream()
				.forEach(scannerInstance -> this.troubleshoot(scannerInstance, riskyPublishers));

	}

	private void troubleshoot(ScannerInstance scannerInstance, Set<SoftwarePublisher> riskyPublishers) {
		Predicate<Software> blacklistedPublisher = software ->
				this.blackListedPublishers.contains(software.getVendor());

		Predicate<Software> riskFactorExceeded = software ->
				riskyPublishers
						.stream()
						.map(SoftwarePublisher::getName)
						.anyMatch(publisher -> publisher.equalsIgnoreCase(software.getVendor()));

		Predicate<Software> riskySoftware = blacklistedPublisher.or(riskFactorExceeded);

		this.reportMongoRepository.findByScannerPid(scannerInstance.getPid())
				.max(Comparator.comparing(SoftwareReport::getCreatedAt))
				.map(SoftwareReport::getSoftware)
				.orElseGet(Collections::emptySet)
				.stream()
				.filter(riskySoftware)
				.collect(collectingAndThen(toSet(), Optional::of))
				.filter(set -> !set.isEmpty())
				.ifPresent(forbiddenSoftware ->
						this.mailSender.sendSoftwareNotificationMessage(scannerInstance, forbiddenSoftware));
	}

}
