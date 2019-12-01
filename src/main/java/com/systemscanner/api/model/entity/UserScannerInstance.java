package com.systemscanner.api.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class UserScannerInstance {

	@EmbeddedId
	private UserScannerInstancePK key;

}
