package com.systemscanner.api.service;

import com.systemscanner.api.model.projection.ReportLight;

import java.util.Set;

public interface ReportService {
	Set<ReportLight> findAllForCurrentUser(String pid, String username);
}
