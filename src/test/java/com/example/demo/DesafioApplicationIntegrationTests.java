package com.example.demo;

			  
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DesafioApplicationIntegrationTests {

	private static final String URL_GITHUB = "https://github.com";
	private static final String URL_GOOGLE = "https://www.google.com.br";
	private static final String SHORT_URL_A1 = "a1";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void healthCheck() throws Exception {
		this.mockMvc.perform(get("/url")).andExpect(status().isOk());
	}
	
	@Test
	public void getUrls() throws Exception {
		this.mockMvc.perform(get("/url")
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$[0].id").value("1"))
					.andExpect(jsonPath("$[0].urlOriginal").value(URL_GOOGLE))
					.andExpect(jsonPath("$[0].urlShort").value(SHORT_URL_A1))
					.andExpect(jsonPath("$[0].expiryDate").value(LocalDateTime.of(2020, 07, 01, 01, 50, 56).toString()));
	}
	
	@Test
	public void redirectShortUrlSucess() throws Exception {
		this.mockMvc.perform(get("/a1"))
					.andExpect(redirectedUrl(URL_GOOGLE)).andExpect(status().isFound());
    }
	
	@Test
	public void redirectShortUrlFail() throws Exception {
		this.mockMvc.perform(get("/fail123"))
					.andExpect(status().isNotFound());
    }
	
	@Test
	public void redirectShortUrlExpired() throws Exception {
		this.mockMvc.perform(get("/b2"))
					.andExpect(status().isGone());
    }
	
	@Test
	public void registerNewShortUrl() throws Exception {
		JSONObject json = new JSONObject();
		json.put("urlOriginal", URL_GITHUB);
		this.mockMvc.perform(post("/url")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json.toString()))
					.andExpect(status().isOk());
    }		
	
	@Test
	public void registerNewShortUrlUrlIsNull() throws Exception {
		JSONObject json = new JSONObject();
		json.put("urlOriginal", null);
		this.mockMvc.perform(post("/url")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json.toString()))
					.andExpect(status().isBadRequest());
    }	
}


