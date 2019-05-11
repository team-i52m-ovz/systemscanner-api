package com.systemscanner.api.service.impl;

import com.systemscanner.api.model.projection.ReportLight;
import com.systemscanner.api.repository.jpa.ReportRepository;
import com.systemscanner.api.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

	private final ReportRepository reportRepository;

	@Override
	public Set<ReportLight> findAllForCurrentUser(String pid, String username) {
		return this.reportRepository.findAllForUser(pid, username);
	}
}
