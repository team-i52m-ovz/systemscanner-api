package com.systemscanner.api.model.mongo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.systemscanner.api.model.mongo.hardware.Win32BIOS;
import com.systemscanner.api.model.mongo.hardware.Win32Baseboard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HardwareInfo {

	@JsonProperty("Win32_BaseBoard")
	private Set<Win32Baseboard> win32Baseboards = Collections.emptySet();

	@JsonProperty("Win32_BIOS")
	private Set<Win32BIOS> win32BIOS = Collections.emptySet();

}
