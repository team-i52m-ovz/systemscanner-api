package com.systemscanner.api.repository;

import com.systemscanner.api.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
