package com.systemscanner.api.service;

import com.systemscanner.api.model.mongo.Report;
import com.systemscanner.api.model.mongo.ReportInfo;

import java.util.Optional;

public interface RatService {

	Optional<Report> createReport(ReportInfo reportInfo, String authentication);

}
