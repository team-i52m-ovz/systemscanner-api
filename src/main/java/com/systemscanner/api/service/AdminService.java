package com.systemscanner.api.service;

import com.systemscanner.api.model.dto.ScannerInstanceBody;
import com.systemscanner.api.model.dto.ScannerInstancePatch;
import com.systemscanner.api.model.projection.DetailedScannerInstance;
import com.systemscanner.api.model.projection.DisplayUser;
import com.systemscanner.api.model.projection.ScannerInstanceLight;

import java.util.Optional;
import java.util.Set;

public interface AdminService {

	Set<DisplayUser> findAllUsers();

	Set<ScannerInstanceLight> findAll();

	Optional<ScannerInstanceLight> createNewInstance(ScannerInstanceBody instance);

	Optional<ScannerInstanceLight> updateInstance(ScannerInstancePatch scannerInstancePatch);

	Optional<DetailedScannerInstance> findByPid(String pid);

	void deleteByPid(String pid);

}
