package com.systemscanner.api.component;

import com.systemscanner.api.model.entity.Role;
import com.systemscanner.api.repository.jpa.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ScannerUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Function<com.systemscanner.api.model.entity.User, Set<GrantedAuthority>> buildAuthorities = user ->
				user.getRoles()
						.stream()
						.map(Role::getName)
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toSet());

		return this.userRepository.findByUsernameOrEmail(username)
				.map(user -> new User(user.getUsername(), user.getPassword(), buildAuthorities.apply(user)))
				.orElseThrow(() -> new UsernameNotFoundException(username));
	}

}
