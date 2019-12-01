package com.systemscanner.api.model.mongo;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "reports")
public class Report {

	@Id
	private String id;

	private String name;

	private String scannerPid;

	private Instant createdAt;

	@JsonUnwrapped
	private ReportInfo details;

}
