package com.systemscanner.api.repository.mongo;

import com.systemscanner.api.model.mongo.Report;
import com.systemscanner.api.model.projection.ReportLight;
import com.systemscanner.api.model.projection.SoftwareReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.stream.Stream;

public interface ReportMongoRepository extends MongoRepository<Report, String> {

	Page<ReportLight> findAllByScannerPid(String scannerPid, Pageable pageable);

	@Query(fields = "{ details.softwareInfo.software: 1, scannerPid: 1, createdAt: 1}")
	Stream<SoftwareReport> findByScannerPid(String scannerPid);

}
