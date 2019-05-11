package com.systemscanner.api.repository.mongo;

import com.systemscanner.api.model.mongo.Report;
import com.systemscanner.api.model.projection.ReportLight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Set;

import static com.systemscanner.api.utils.QueryProperties.JsonParams;

public interface ReportMongoRepository extends MongoRepository<Report, String> {

	@Query(JsonParams.EMPTY_QUERY)
	Set<Report> findAllDistinct();

	Set<Report> findAllByHardwareLike(String like);

}
