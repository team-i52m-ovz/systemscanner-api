package com.systemscanner.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.systemscanner.api.ApiApplication;
import com.systemscanner.api.model.dto.NewScannerInstance;
import com.systemscanner.api.utils.UtilsComponent;
import lombok.Setter;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScannerControllerIT {
	private static final String BASE_PATH = "/scanner/instances";

	@Setter(onMethod_ = {@Autowired})
	private ObjectMapper objectMapper;

	@Setter(onMethod_ = {@Autowired})
	private WebApplicationContext context;

	@Setter(onMethod_ = {@Autowired})
	private UtilsComponent utilsComponent;

	private Authentication principal;

	private MockMvc mockMvc;

	@Before
	public void initialize() {
		val principal = this.utilsComponent.fakePrincipal();

		this.objectMapper.disable(SerializationFeature.WRAP_ROOT_VALUE);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		this.principal = new UsernamePasswordAuthenticationToken(principal.getUsername(), principal.getPassword(),
				Collections.emptyList());
	}

	@Test
	public void createNewInstance() throws Exception {
		val body = new NewScannerInstance(UUID.randomUUID().toString(), UUID.randomUUID().toString());

		this.mockMvc.perform(
				post(BASE_PATH)
						.contentType(MediaType.APPLICATION_JSON)
						.content(this.objectMapper.writeValueAsString(body))
						.principal(this.principal)
		)
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	public void declineNewInstance() throws Exception {
		this.mockMvc.perform(post(BASE_PATH).accept(MediaType.APPLICATION_JSON).principal(this.principal))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void findAllForPrincipal() throws Exception {
		this.mockMvc.perform(get(BASE_PATH).accept(MediaType.APPLICATION_JSON).principal(this.principal))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
	}

}
