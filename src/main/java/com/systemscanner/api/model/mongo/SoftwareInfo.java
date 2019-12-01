package com.systemscanner.api.model.mongo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.systemscanner.api.model.mongo.software.Software;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoftwareInfo {

	@JsonProperty("Win32_Product")
	private Set<Software> software;

}
