package com.systemscanner.api.repository.jpa;

import com.systemscanner.api.model.entity.User;
import com.systemscanner.api.model.projection.DisplayUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT user FROM User user")
	Set<DisplayUser> findAllLight();

	@Query("SELECT u FROM User u " +
			" LEFT JOIN FETCH u.roles" +
			" WHERE LOWER(u.username) = LOWER(:uid)" +
			" OR LOWER(u.email) = LOWER(:uid)")
	Optional<User> findByUsernameOrEmail(@Param("uid") String uid);

	@Query("SELECT user FROM User user" +
			" JOIN UserScannerInstance usi ON usi.key.userId = user.id" +
			" AND usi.key.scannerInstancePid = :scannerInstancePid")
	Set<DisplayUser> findAllByScannerInstancePid(@Param("scannerInstancePid") String scannerInstancePid);

}
