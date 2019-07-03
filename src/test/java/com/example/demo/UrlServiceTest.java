package com.example.demo;


import java.time.LocalDateTime;

import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.repository.Urls;
import com.example.demo.service.UrlService;

public class UrlServiceTest {
	
	@MockBean
	private UrlService urlService;
	
	@MockBean
	private Urls urlRepository;
	
	@Test
	public void redirectShortUrlSucess() throws Exception {
		//final Url url = new Url(1L, "http://www.globo.com", "/short", LocalDate.now();
		System.out.println(LocalDateTime.now());
	}

}
