package com.systemscanner.api.service;

import com.systemscanner.api.model.dto.RatReportDTO;
import com.systemscanner.api.model.entity.Report;

public interface RatService {
	Report createReport(RatReportDTO ratReportDTO, String scannerPid, String scannerToken);
}
