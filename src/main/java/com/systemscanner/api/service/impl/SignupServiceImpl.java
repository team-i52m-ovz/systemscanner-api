package com.systemscanner.api.service.impl;

import com.systemscanner.api.model.dto.SignupDTO;
import com.systemscanner.api.model.entity.User;
import com.systemscanner.api.model.projection.UserLight;
import com.systemscanner.api.repository.jpa.UserRepository;
import com.systemscanner.api.service.SignupService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignupServiceImpl implements SignupService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final ProjectionFactory projectionFactory;

	@Override
	public UserLight signup(SignupDTO signupDTO) {
		val user = User.builder()
				.email(signupDTO.getEmail())
				.username(signupDTO.getUsername())
				.password(this.passwordEncoder.encode(signupDTO.getPassword()))
				.build();

		return Optional.of(this.userRepository.save(user))
				.map(u -> this.projectionFactory.createProjection(UserLight.class, u))
				.orElseThrow(() -> new DataIntegrityViolationException("Invalid registration form value"));
	}

}
