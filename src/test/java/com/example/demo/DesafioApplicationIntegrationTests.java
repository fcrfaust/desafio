package com.example.demo;

			  
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
					.andExpect(jsonPath("$[0].urlOriginal").value("https://www.google.com.br"))
					.andExpect(jsonPath("$[0].urlShort").value("a1"))
					.andExpect(jsonPath("$[0].expiryDate").value("2020-07-01T04:50:56.000+0000"));
	}
	
	@Test
	public void redirectShortUrlSucess() throws Exception {
		this.mockMvc.perform(get("/a1"))
					.andExpect(redirectedUrl("https://www.google.com.br")).andExpect(status().isFound());
    }
	
	@Test
	public void redirectShortUrlFail() throws Exception {
		this.mockMvc.perform(get("/failfailfailfailfail123"))
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
		json.put("urlOriginal", "https://github.com");
		this.mockMvc.perform(post("/url")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json.toString()))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.urlOriginal").value("https://github.com"))
					.andExpect(jsonPath("$.urlShort").isNotEmpty())
					.andExpect(jsonPath("$.expiryDate").isNotEmpty());
    }		
}


