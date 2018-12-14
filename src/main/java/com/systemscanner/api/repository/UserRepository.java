package com.systemscanner.api.repository;

import com.systemscanner.api.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:uid)" +
			" OR LOWER(u.email) = LOWER(:uid)")
	Optional<User> findByUsernameOrEmail(@Param("uid") String uid);
}
