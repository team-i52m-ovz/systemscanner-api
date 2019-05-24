package com.systemscanner.api.service.impl;

import com.systemscanner.api.model.dto.NewScannerInstance;
import com.systemscanner.api.model.entity.ScannerInstance;
import com.systemscanner.api.model.entity.User;
import com.systemscanner.api.model.projection.ScannerInstanceLight;
import com.systemscanner.api.repository.jpa.ScannerInstanceRepository;
import com.systemscanner.api.repository.jpa.UserRepository;
import com.systemscanner.api.service.ScannerInstanceService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ScannerInstanceServiceImpl implements ScannerInstanceService {

	private final ScannerInstanceRepository scannerInstanceRepository;
	private final ProjectionFactory projectionFactory;
	private final UserRepository userRepository;

	@Override
	public Set<ScannerInstanceLight> findAllForCurrentUser(String username) {
		return this.scannerInstanceRepository.findAllForUser(username);
	}

	@Override
	@Transactional
	public Optional<ScannerInstanceLight> createNewInstance(NewScannerInstance instance, String username) {
		return this.userRepository.findByUsernameOrEmail(username)
				.map(user -> this.createInternal(instance, user))
				.map(inst -> this.projectionFactory.createProjection(ScannerInstanceLight.class, inst));
	}

	private ScannerInstance createInternal(NewScannerInstance instance, User user) {
		val entity = this.scannerInstanceRepository.saveAndFlush(this.toEntity(instance));

		user.getScannerInstances().add(entity);
		this.userRepository.save(user);

		return entity;
	}

	private ScannerInstance toEntity(NewScannerInstance instance) {
		return ScannerInstance.builder()
				.pid(instance.getPid())
				.securityKey(instance.getToken())
				.build();
	}
}
