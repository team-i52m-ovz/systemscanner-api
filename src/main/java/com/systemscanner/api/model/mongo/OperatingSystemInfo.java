package com.systemscanner.api.model.mongo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.systemscanner.api.model.mongo.os.Win32ComputerSystem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperatingSystemInfo {

	@JsonProperty("Win32_ComputerSystem")
	private Set<Win32ComputerSystem> win32ComputerSystems = Collections.emptySet();

}
