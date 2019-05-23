package com.systemscanner.api.controllers;

import com.systemscanner.api.ApiApplication;
import lombok.Setter;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

import static com.systemscanner.api.utils.HttpProperties.HttpHeaders.SCANNER_PID;
import static com.systemscanner.api.utils.HttpProperties.HttpHeaders.SCANNER_TOKEN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RatControllerIT {
	private static final String BASE_PATH = "/rat";

	@Setter(onMethod_ = {@Autowired})
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void initialize() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void declineRequest() throws Exception {
		this.mockMvc.perform(post(BASE_PATH).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test(expected = IllegalArgumentException.class)
	public void acceptReport() throws Exception {
		val token = UUID.randomUUID().toString();
		val pid = UUID.randomUUID().toString();

		val uri = UriComponentsBuilder.fromHttpUrl(BASE_PATH)
				.queryParam(SCANNER_PID, pid, SCANNER_TOKEN, token)
				.toUriString();

		this.mockMvc.perform(post(uri).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}
}
