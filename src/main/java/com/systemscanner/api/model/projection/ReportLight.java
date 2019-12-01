package com.systemscanner.api.model.projection;

import java.time.Instant;

public interface ReportLight {

	String getId();

	String getScannerPid();

	Instant getCreatedAt();

}
