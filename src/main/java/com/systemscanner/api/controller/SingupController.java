package com.systemscanner.api.controller;

import com.systemscanner.api.model.dto.SignupDTO;
import com.systemscanner.api.model.projection.UserLight;
import com.systemscanner.api.service.SignupService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
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
