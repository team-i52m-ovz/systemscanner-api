package com.systemscanner.api.repository.mongo;

import com.systemscanner.api.model.mongo.Report;
import com.systemscanner.api.model.projection.ReportLight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportMongoRepository extends MongoRepository<Report, String> {

	Page<ReportLight> findAllByScannerPid(String scannerPid, Pageable pageable);

}
