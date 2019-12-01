package com.systemscanner.api.model.projection;

import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface DetailedScannerInstance {

	String getPid();

	String getName();

	@Value("#{@scannerDetailsResolver.fetchScannerInstanceAssignees(target.pid)}")
	Set<DisplayUser> getAssignees();

}
