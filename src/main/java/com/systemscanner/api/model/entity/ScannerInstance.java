package com.systemscanner.api.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScannerInstance {

	@Id
	@Column(length = 50)
	private String pid;

	@Column(nullable = false, length = 50)
	private String securityKey;

}
