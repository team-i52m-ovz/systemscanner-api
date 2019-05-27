package com.systemscanner.api.model.mongo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportInfo {

	@JsonProperty("Win32_Baseboard")
	private Set<Win32Baseboard> win32Baseboards = Collections.emptySet();

	@JsonProperty("Win32_BIOS")
	private Set<Win32BIOS> win32BIOS = Collections.emptySet();

	@JsonProperty("Win32_ComputerSystem")
	private Set<Win32ComputerSystem> win32ComputerSystems = Collections.emptySet();

}
