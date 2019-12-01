package com.systemscanner.api.component;

import com.systemscanner.api.model.projection.DisplayUser;
import com.systemscanner.api.repository.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ScannerDetailsResolver {

	private final UserRepository userRepository;

	public Set<DisplayUser> fetchScannerInstanceAssignees(String pid) {
		return this.userRepository.findAllByScannerInstancePid(pid);
	}

}
