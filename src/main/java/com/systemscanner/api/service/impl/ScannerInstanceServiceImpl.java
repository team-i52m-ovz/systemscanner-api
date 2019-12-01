package com.systemscanner.api.service.impl;

import com.systemscanner.api.model.projection.ScannerInstanceLight;
import com.systemscanner.api.repository.jpa.ScannerInstanceRepository;
import com.systemscanner.api.service.ScannerInstanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ScannerInstanceServiceImpl implements ScannerInstanceService {

	private final ScannerInstanceRepository scannerInstanceRepository;

	@Override
	public Set<ScannerInstanceLight> findAllForCurrentUser(String username) {
		return this.scannerInstanceRepository.findAllForUser(username);
	}

	@Override
	public Optional<ScannerInstanceLight> findOne(String pid, String username) {
		return this.scannerInstanceRepository.findOneByUserAndPid(pid, username);
	}

}
