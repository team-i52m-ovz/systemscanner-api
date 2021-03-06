package com.systemscanner.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupDTO {

	@NotEmpty
	private String username;

	@NotEmpty
	private String email;

	@NotEmpty
	private String password;
}
