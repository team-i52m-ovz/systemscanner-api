package com.systemscanner.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewScannerInstance {

	@NotEmpty
	@Size(min = 30, max = 50)
	private String pid;

	@NotEmpty
	@Size(min = 30, max = 50)
	private String token;

}
