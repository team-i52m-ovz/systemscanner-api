package com.systemscanner.api.utils;

import com.systemscanner.api.model.entity.User;
import com.systemscanner.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UtilsComponent {

	private final UserRepository userRepository;

	public User fakePrincipal() {
		val user = User.builder()
				.username("Admin")
				.password(new BCryptPasswordEncoder().encode("admin"))
				.email("admin@admin.com")
				.scannerInstances(Collections.emptySet())
				.build();

		return this.userRepository.findByUsernameOrEmail(user.getUsername())
				.orElseGet(() -> this.userRepository.save(user));
	}
}
