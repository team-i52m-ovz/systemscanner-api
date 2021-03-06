package com.systemscanner.api.service;

import com.systemscanner.api.model.mongo.Report;
import com.systemscanner.api.model.mongo.ReportInfo;

import java.util.Optional;

public interface ScannerService {

	Optional<String> registerInstance(String scannerSecret, String remoteAddress);

	Optional<Report> createReport(ReportInfo reportInfo, String authentication);

}
