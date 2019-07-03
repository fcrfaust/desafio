package com.example.demo;


import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.service.UrlService;

public class UrlServiceTest {
	
	@MockBean
	private UrlService urlService;
	
	@Test
	public void redirectShortUrlSucess() throws Exception {
		
	}

}
