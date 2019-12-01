package com.systemscanner.api.repository.jpa;

import com.systemscanner.api.model.entity.UserScannerInstance;
import com.systemscanner.api.model.entity.UserScannerInstancePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserScannerInstanceRepository extends JpaRepository<UserScannerInstance, UserScannerInstancePK> {

	@Modifying
	@Query("DELETE FROM UserScannerInstance usi WHERE usi.key.scannerInstancePid = :pid")
	void deleteAllByScannerInstancePid(@Param("pid") String pid);

}
