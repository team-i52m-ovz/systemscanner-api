package com.systemscanner.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportRestFilter {

	@NotEmpty
	private String pid;

	private Integer page = 0;

	private Integer size = 15;
}