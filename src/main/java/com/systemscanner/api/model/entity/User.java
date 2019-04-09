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

	@Column(nullable = false, length = 50)
	private String username;

	@Column(nullable = false, length = 30)
	private String password;

	@Column(nullable = false)
	private String email;

	@ManyToMany
	@JoinTable(name = "user_scanner_instance",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "scanner_instance_pid"))
	private Set<ScannerInstance> scannerInstances;
}
