package com.systemscanner.api.service;

import com.systemscanner.api.model.projection.ScannerInstanceLight;

import java.util.Optional;
import java.util.Set;

public interface ScannerInstanceService {

	Set<ScannerInstanceLight> findAllForCurrentUser(String username);

	Optional<ScannerInstanceLight> findOne(String pid, String username);

}
