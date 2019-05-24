package com.systemscanner.api.service;

import com.systemscanner.api.model.mongo.Report;
import com.systemscanner.api.model.projection.ReportLight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReportService {

	Page<ReportLight> findAllForCurrentUser(String pid, String username, Pageable pageable);

	Optional<Report> findById(String id, String username);

}
