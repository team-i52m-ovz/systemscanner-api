package com.systemscanner.api.model.mongo.hardware;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Win32BIOS {

	@JsonProperty("BuildNumber")
	private String buildNumber;

	@JsonProperty("CodeSet")
	private String codeSet;

	@JsonProperty("CurrentLanguage")
	private String currentLanguage;

	@JsonProperty("Description")
	private String description;

	@JsonProperty("EmbeddedControllerMajorVersion")
	private String embeddedControllerMajorVersion;

	@JsonProperty("EmbeddedControllerMinorVersion")
	private String embeddedControllerMinorVersion;

	@JsonProperty("InstallDate")
	private String installDate;

	@JsonProperty("Manufacturer")
	private String manufacturer;

	@JsonProperty("Name")
	private String name;

	@JsonProperty("PrimaryBIOS")
	private String primaryBIOS;

	@JsonProperty("SerialNumber")
	private String serialNumber;

	@JsonProperty("SoftwareElementID")
	private String softwareElementId;

	@JsonProperty("SystemBiosMajorVersion")
	private String systemBiosMajorVersion;

	@JsonProperty("SystemBiosMinorVersion")
	private String systemBiosMinorVersion;

	@JsonProperty("TargetOperatingSystem")
	private String targetOperatingSystem;

	@JsonProperty("Version")
	private String version;

}
