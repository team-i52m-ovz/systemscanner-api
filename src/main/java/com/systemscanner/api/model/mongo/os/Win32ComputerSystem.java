package com.systemscanner.api.model.mongo.os;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Win32ComputerSystem {

	@JsonProperty("AdminPasswordStatus")
	private String adminPasswordStatus;

	@JsonProperty("BootupState")
	private String bootupState;

	@JsonProperty("Caption")
	private String caption;

	@JsonProperty("CreationClassName")
	private String creationClassName;

	@JsonProperty("DNSHostName")
	private String DNSHostName;

	@JsonProperty("Domain")
	private String domain;

	@JsonProperty("LastLoadInfo")
	private String lastLoadInfo;

	@JsonProperty("Manufacturer")
	private String manufacturer;

	@JsonProperty("Model")
	private String model;

	@JsonProperty("Name")
	private String name;

	@JsonProperty("NetworkServerModeEnabled")
	private String networkServerModeEnabled;

	@JsonProperty("NumberOfLogicalProcessors")
	private String numberOfLogicalProcessors;

	@JsonProperty("NumberOfProcessors")
	private String numberOfProcessors;

	@JsonProperty("PowerManagementSupported")
	private String powerManagementSupported;

	@JsonProperty("PrimaryOwnerName")
	private String primaryOwnerName;

	@JsonProperty("SystemFamily")
	private String systemFamily;

	@JsonProperty("UserName")
	private String userName;

	@JsonProperty("WakeUpType")
	private String wakeUpType;

	@JsonProperty("Workgroup")
	private String workgroup;

}
