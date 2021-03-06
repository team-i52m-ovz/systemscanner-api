package com.systemscanner.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScannerInstancePatch {

	@NotEmpty
	private String pid;

	@NotEmpty
	@Size(min = 5, max = 100)
	private String name;

	@NotNull
	private Set<Long> users;

}
