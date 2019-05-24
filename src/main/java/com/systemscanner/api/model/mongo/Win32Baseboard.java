package com.systemscanner.api.model.mongo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Win32Baseboard {

	@JsonProperty("Depth")
	private String depth;

	@JsonProperty("Description")
	private String description;

	@JsonProperty("Height")
	private String height;

	@JsonProperty("HostingBoard")
	private String hostingBoard;

	@JsonProperty("InstallDate")
	private String installDate;

	@JsonProperty("Manufacturer")
	private String manufacturer;

	@JsonProperty("Model")
	private String model;

	@JsonProperty("Name")
	private String name;

	@JsonProperty("PartNumber")
	private String partNumber;

	@JsonProperty("PoweredOn")
	private String poweredOn;

	@JsonProperty("Product")
	private String product;

	@JsonProperty("Removable")
	private String removable;

	@JsonProperty("Replaceable")
	private String replaceable;

	@JsonProperty("RequirementsDescription")
	private String requirementsDescription;

	@JsonProperty("RequiresDaughterBoard")
	private String requiresDaughterBoard;

	@JsonProperty("SerialNumber")
	private String serialNumber;

	@JsonProperty("SpecialRequirements")
	private String specialRequirements;

	@JsonProperty("Status")
	private String status;

	@JsonProperty("Weight")
	private String weight;

	@JsonProperty("Width")
	private String width;
}
