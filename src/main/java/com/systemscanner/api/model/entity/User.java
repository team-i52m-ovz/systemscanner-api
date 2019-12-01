package com.systemscanner.api.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "public")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, length = 40)
	private String firstName;

	@Column(nullable = false, length = 40)
	private String lastName;

	@Column(nullable = false, unique = true)
	private String email;

	@ManyToMany
	@JoinTable(name = "user_role", inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

}
