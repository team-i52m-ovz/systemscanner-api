package com.systemscanner.api.component;

import com.systemscanner.api.repository.jpa.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@AllArgsConstructor
public class ScannerUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userRepository.findByUsernameOrEmail(username)
				.map(user -> new User(user.getUsername(), user.getPassword(), Collections.emptySet()))
				.orElseThrow(() -> new UsernameNotFoundException(username));
	}
}
