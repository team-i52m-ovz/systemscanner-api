package com.systemscanner.api.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UserScannerInstancePK implements Serializable {

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false, length = 50)
	private String scannerInstancePid;

}
