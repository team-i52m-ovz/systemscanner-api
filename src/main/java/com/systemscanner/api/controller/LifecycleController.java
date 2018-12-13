package com.systemscanner.api.controller;

import com.systemscanner.api.model.entity.User;
import com.systemscanner.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController("/lifecycle")
public class LifecycleController {

	private final UserRepository userRepository;

	@PostMapping
	public String success() {
		return "Success";
	}

	/*@PostMapping
	public User create(@RequestBody User user) {
		return this.userRepository.save(user);
	}*/
}
