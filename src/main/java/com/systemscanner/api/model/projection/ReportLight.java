package com.systemscanner.api.model.projection;

import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;

public interface ReportLight {

	Long getId();

	@Value("#{target.scannerInstance.pid}")
	String getInstancePid();

	Instant getCreated();
}
