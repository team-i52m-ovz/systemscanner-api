package com.systemscanner.api.service;

import com.systemscanner.api.model.projection.ScannerInstanceLight;

import java.util.Set;

public interface ScannerInstanceService {

	Set<ScannerInstanceLight> findAllForCurrentUser(String username);

}
