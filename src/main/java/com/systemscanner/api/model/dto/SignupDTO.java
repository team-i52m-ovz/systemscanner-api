package com.systemscanner.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupDTO {

	@NotNull
	private String username;

	@NotNull
	private String email;

	@NotNull
	private String password;
}
