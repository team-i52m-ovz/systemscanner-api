package com.systemscanner.api.repository.jpa;

import com.systemscanner.api.model.entity.Report;
import com.systemscanner.api.model.projection.ReportLight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ReportRepository extends JpaRepository<Report, Long> {

	@Query("SELECT new Report(report.id, instance, report.created) FROM User user" +
			" JOIN user.scannerInstances instance" +
			" JOIN Report report ON report.scannerInstance.pid = instance.pid" +
			" WHERE (LOWER(user.username) = LOWER(:userUid) OR LOWER(user.email) = LOWER(:userUid))" +
			" AND report.scannerInstance.pid = :pid")
	Set<ReportLight> findAllForUser(@Param("pid") String pid, @Param("userUid") String userUid);
}
