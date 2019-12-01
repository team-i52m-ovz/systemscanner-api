package com.systemscanner.api.service.impl;

import com.systemscanner.api.model.dto.ScannerInstanceBody;
import com.systemscanner.api.model.dto.ScannerInstancePatch;
import com.systemscanner.api.model.entity.ScannerInstance;
import com.systemscanner.api.model.entity.User;
import com.systemscanner.api.model.entity.UserScannerInstance;
import com.systemscanner.api.model.entity.UserScannerInstancePK;
import com.systemscanner.api.model.projection.DetailedScannerInstance;
import com.systemscanner.api.model.projection.DisplayUser;
import com.systemscanner.api.model.projection.ScannerInstanceLight;
import com.systemscanner.api.repository.jpa.ScannerInstanceRepository;
import com.systemscanner.api.repository.jpa.UserRepository;
import com.systemscanner.api.repository.jpa.UserScannerInstanceRepository;
import com.systemscanner.api.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final UserRepository userRepository;
	private final ProjectionFactory projectionFactory;
	private final ScannerInstanceRepository scannerInstanceRepository;
	private final UserScannerInstanceRepository userScannerInstanceRepository;

	@Override
	public Set<DisplayUser> findAllUsers() {
		return this.userRepository.findAllLight();
	}

	@Override
	public Set<ScannerInstanceLight> findAll() {
		return this.scannerInstanceRepository.findAllLight();
	}

	@Override
	public Optional<DetailedScannerInstance> findByPid(String pid) {
		return this.scannerInstanceRepository.findDetailsByPid(pid);
	}

	@Override
	public void deleteByPid(String pid) {
		this.scannerInstanceRepository.deleteById(pid);
	}

	@Override
	@Transactional
	public Optional<ScannerInstanceLight> createNewInstance(ScannerInstanceBody instance) {
		return Optional.of(instance)
				.map(this::toEntity)
				.map(this.scannerInstanceRepository::save)
				.map(entity -> this.assignToUsers(entity, instance.getUsers()))
				.map(inst -> this.projectionFactory.createProjection(ScannerInstanceLight.class, inst));
	}

	@Override
	@Transactional
	public Optional<ScannerInstanceLight> updateInstance(ScannerInstancePatch scannerInstancePatch) {
		UnaryOperator<ScannerInstance> updateFields = sc -> {
			sc.setName(scannerInstancePatch.getName());

			return sc;
		};

		return this.scannerInstanceRepository.findById(scannerInstancePatch.getPid())
				.map(updateFields)
				.map(this.scannerInstanceRepository::save)
				.map(scannerInstance -> this.updateAssignees(scannerInstance, scannerInstancePatch))
				.map(inst -> this.projectionFactory.createProjection(ScannerInstanceLight.class, inst));
	}

	private ScannerInstance updateAssignees(ScannerInstance scannerInstance, ScannerInstancePatch updated) {
		UnaryOperator<ScannerInstance> cleanUpOldUsers = sc -> {
			this.userScannerInstanceRepository.deleteAllByScannerInstancePid(scannerInstance.getPid());

			return sc;
		};

		return cleanUpOldUsers
				.andThen(sc -> this.assignToUsers(sc, updated.getUsers()))
				.apply(scannerInstance);
	}

	private ScannerInstance assignToUsers(ScannerInstance scannerInstance, Set<Long> users) {
		Function<Long, UserScannerInstancePK> buildUserScannerInstanceKey = userId ->
				UserScannerInstancePK.builder()
						.userId(userId)
						.scannerInstancePid(scannerInstance.getPid())
						.build();

		this.userRepository.findAllById(users)
				.stream()
				.map(User::getId)
				.map(buildUserScannerInstanceKey)
				.map(UserScannerInstance::of)
				.collect(collectingAndThen(toSet(), Optional::of))
				.filter(set -> !set.isEmpty())
				.ifPresent(this.userScannerInstanceRepository::saveAll);

		return scannerInstance;
	}

	private ScannerInstance toEntity(ScannerInstanceBody instance) {
		return ScannerInstance.builder()
				.name(instance.getName())
				.pid(instance.getPid())
				.securityKey(instance.getToken())
				.build();
	}

}
