package com.systemscanner.api.model.projection;

import com.systemscanner.api.model.mongo.software.Software;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.util.Set;

public interface SoftwareReport {

	String getScannerPid();

	Instant getCreatedAt();

	@Value("#{target.details.softwareInfo.software}")
	Set<Software> getSoftware();

}
