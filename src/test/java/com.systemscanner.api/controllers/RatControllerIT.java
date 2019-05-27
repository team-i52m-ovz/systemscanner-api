package com.systemscanner.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.systemscanner.api.ApiApplication;
import com.systemscanner.api.model.entity.ScannerInstance;
import com.systemscanner.api.model.mongo.ReportInfo;
import com.systemscanner.api.repository.jpa.ScannerInstanceRepository;
import com.systemscanner.api.repository.mongo.ReportMongoRepository;
import lombok.Setter;
import lombok.val;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Base64;
import java.util.UUID;
import java.util.function.Function;

import static com.systemscanner.api.utils.HttpProperties.HttpHeaders.SCANNER;
import static com.systemscanner.api.utils.HttpProperties.TokenUtils.BASIC_AUTH_PREFIX;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RatControllerIT {
	private static final String BASE_PATH = "/rat";
	private static final String PID = "aaaa-bbbb-cccc";
	private static final String SECURITY_KEY = "security_token_to_be_encoded";

	private MockMvc mockMvc;

	@Setter(onMethod_ = {@Autowired})
	private ObjectMapper objectMapper;

	@Setter(onMethod_ = {@Autowired})
	private WebApplicationContext context;

	@Setter(onMethod_ = {@Autowired})
	private ReportMongoRepository reportMongoRepository;

	@Setter(onMethod_ = {@Autowired})
	private ScannerInstanceRepository scannerInstanceRepository;

	@Before
	public void initialize() {
		this.objectMapper.disable(SerializationFeature.WRAP_ROOT_VALUE);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

		val scannerInstance = ScannerInstance.builder()
				.pid(PID)
				.securityKey(SECURITY_KEY)
				.build();

		this.scannerInstanceRepository.save(scannerInstance);
	}

	@After
	public void cleanup() {
		this.reportMongoRepository.deleteAll();
		this.scannerInstanceRepository.deleteAll();
	}

	@Test
	public void declineRequest() throws Exception {
		this.mockMvc.perform(post(BASE_PATH).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void unauthorizedReport() throws Exception {
		val pid = UUID.randomUUID().toString();
		val token = UUID.randomUUID().toString();

		val emptyBody = this.objectMapper.writer().writeValueAsString(new ReportInfo());

		this.mockMvc.perform(
				post(BASE_PATH)
						.contentType(MediaType.APPLICATION_JSON)
						.header(SCANNER, this.toBasicAuthToken(pid, token))
						.content(emptyBody)
		)
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void acceptReport() throws Exception {
		val emptyBody = this.objectMapper.writer().writeValueAsString(new ReportInfo());

		this.mockMvc.perform(
				post(BASE_PATH)
						.contentType(MediaType.APPLICATION_JSON)
						.header(SCANNER, this.toBasicAuthToken(PID, SECURITY_KEY))
						.content(emptyBody)
		)
				.andExpect(status().isAccepted());

		val reports = this.reportMongoRepository.findAllByScannerPid(PID, PageRequest.of(1, 1));

		assertTrue(reports.getTotalElements() > 0);
	}

	private String toBasicAuthToken(String pid, String token) {
		val encoder = Base64.getEncoder();

		Function<String, String> toBasicAuthToken = str ->
				BASIC_AUTH_PREFIX.concat(new String(encoder.encode(str.getBytes())));

		return toBasicAuthToken.apply(String.format("%s:%s", pid, token));
	}
}
