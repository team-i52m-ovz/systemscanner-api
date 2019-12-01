package com.systemscanner.api.controller;

import com.systemscanner.api.model.dto.SignupDTO;
import com.systemscanner.api.model.projection.UserLight;
import com.systemscanner.api.service.SignupService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class SingupController {

	private final SignupService signupService;

	@PostMapping("/signup")
	public UserLight signup(@Valid @RequestBody SignupDTO signupDTO) {
		return this.signupService.signup(signupDTO);
	}

}
