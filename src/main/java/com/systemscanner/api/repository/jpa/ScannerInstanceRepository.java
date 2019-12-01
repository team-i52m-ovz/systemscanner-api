package com.systemscanner.api.repository.jpa;

import com.systemscanner.api.model.entity.ScannerInstance;
import com.systemscanner.api.model.projection.DetailedScannerInstance;
import com.systemscanner.api.model.projection.ScannerInstanceLight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface ScannerInstanceRepository extends JpaRepository<ScannerInstance, String> {

	@Query("SELECT scannerInstance FROM ScannerInstance scannerInstance" +
			" JOIN UserScannerInstance usi ON usi.key.scannerInstancePid = scannerInstance.pid" +
			" JOIN User user ON user.id = usi.key.userId" +
			" AND LOWER(user.username) = LOWER(:userUid)" +
			" OR LOWER(user.email) = LOWER(:userUid)")
	Set<ScannerInstanceLight> findAllForUser(@Param("userUid") String userUid);

	@Query("SELECT scannerInstance FROM ScannerInstance scannerInstance" +
			" JOIN UserScannerInstance usi ON usi.key.scannerInstancePid = scannerInstance.pid" +
			" JOIN User user ON user.id = usi.key.userId" +
			" AND LOWER(user.username) = LOWER(:userUid)" +
			" OR LOWER(user.email) = LOWER(:userUid)" +
			" WHERE scannerInstance.pid = :pid")
	Optional<ScannerInstanceLight> findOneByUserAndPid(@Param("userUid") String userUid, @Param("pid") String pid);

	Optional<ScannerInstance> findByPidAndSecurityKey(String pid, String securityKey);

	@Query("SELECT scannerInstance FROM ScannerInstance scannerInstance")
	Set<ScannerInstanceLight> findAllLight();

	@Query("SELECT scannerInstance FROM ScannerInstance scannerInstance" +
			" WHERE scannerInstance.pid = :pid")
	Optional<DetailedScannerInstance> findDetailsByPid(@Param("pid") String pid);

}
