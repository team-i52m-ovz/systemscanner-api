package com.systemscanner.api.service.impl;

import com.systemscanner.api.model.entity.ScannerInstance;
import com.systemscanner.api.model.entity.User;
import com.systemscanner.api.model.projection.ScannerInstanceLight;
import com.systemscanner.api.repository.ScannerInstanceRepository;
import com.systemscanner.api.repository.UserRepository;
import com.systemscanner.api.service.ScannerInstanceService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class ScannerInstanceServiceImpl implements ScannerInstanceService {

	private final ScannerInstanceRepository scannerInstanceRepository;
	private final UserRepository userRepository;

	@Override
	public Set<ScannerInstanceLight> findAllForCurrentUser(String username) {
		return this.scannerInstanceRepository.findAllForUser(username);
	}

	@Override
	@Transactional
	public ScannerInstance createNewInstance(String username) {
		return this.userRepository.findByUsernameOrEmail(username)
				.map(this::createInternal)
				.orElseThrow(() -> new UsernameNotFoundException(username));
	}

	private ScannerInstance createInternal(User user) {
		val instance =
				this.scannerInstanceRepository.saveAndFlush(
						ScannerInstance.builder()
								.pid(UUID.randomUUID().toString())
								.securityKey(UUID.randomUUID().toString())
								.build()
				);
		user.getScannerInstances().add(instance);
		this.userRepository.save(user);
		return instance;
	}
}
