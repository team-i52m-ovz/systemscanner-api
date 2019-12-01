package com.systemscanner.api.repository.jpa;

import com.systemscanner.api.model.entity.ScannerInstance;
import com.systemscanner.api.model.projection.ScannerInstanceLight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface ScannerInstanceRepository extends JpaRepository<ScannerInstance, String> {

	@Query("SELECT new ScannerInstance(sci.pid, sci.securityKey) FROM User user " +
			" JOIN user.scannerInstances sci" +
			" WHERE LOWER(user.username) = LOWER(:userUid)" +
			" OR LOWER(user.email) = LOWER(:userUid)")
	Set<ScannerInstanceLight> findAllForUser(@Param("userUid") String userUid);

	@Query("SELECT new ScannerInstance(sci.pid, sci.securityKey) FROM User user " +
			" JOIN user.scannerInstances sci" +
			" WHERE sci.pid = :pid" +
			" AND (LOWER(user.username) = LOWER(:userUid) OR LOWER(user.email) = LOWER(:userUid))")
	Optional<ScannerInstanceLight> findOneByUserAndPid(@Param("userUid") String userUid, @Param("pid") String pid);

	Optional<ScannerInstance> findByPidAndSecurityKey(String pid, String securityKey);

}
