package com.example.demo;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.xml.ws.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import com.example.demo.model.Url;
import com.example.demo.repository.Urls;
import com.example.demo.service.UrlService;

@RunWith(SpringRunner.class)
public class UrlServiceTest {
	
	private static final String SHOR_URL_SHORT = "/short";

	private static final String SHORT_URL_B2C3 = "b2c3";

	private static final String ULR_GLOBO = "http://www.globo.com";

	@Spy
	@InjectMocks
	private UrlService urlService;
	
	@Mock
	private Urls urlRepository;
	
	@Test
	public void expiryDateIsbiggerThanNow() throws Exception {
		final Url url = new Url(1L, ULR_GLOBO, SHOR_URL_SHORT, LocalDateTime.of(2020, 07, 01, 01, 50, 51));	
		LocalDateTime dateTest = LocalDateTime.of(2020, 07, 01, 01, 50, 50);
		when(urlService.returnDateNow()).thenReturn(dateTest); 
		when(urlRepository.findByShortUrl(SHORT_URL_B2C3)).thenReturn(url);
		assertThat(urlService.findRedirect(SHORT_URL_B2C3).getStatusCodeValue(), is(302));
	 }
	
	@Test
	public void expiryDateIsSameNow() throws Exception {
		LocalDateTime dateTest = LocalDateTime.of(2020, 07, 01, 01, 50, 56);
		final Url url = new Url(1L, ULR_GLOBO, SHOR_URL_SHORT, LocalDateTime.of(2020, 07, 01, 01, 50, 56));	
		 when(urlRepository.findByShortUrl(SHORT_URL_B2C3)).thenReturn(url);
		 when(urlService.returnDateNow()).thenReturn(dateTest);
		 assertThat(urlService.findRedirect(SHORT_URL_B2C3).getStatusCodeValue(), is(302));
	 }
	
	@Test
	public void expiryDateIsSmallerThanNow() throws Exception {
		LocalDateTime dateTest = LocalDateTime.of(2020, 07, 01, 01, 50, 57);
		final Url url = new Url(1L, ULR_GLOBO, SHOR_URL_SHORT, LocalDateTime.of(2020, 07, 01, 01, 50, 56));	
		 when(urlRepository.findByShortUrl(SHORT_URL_B2C3)).thenReturn(url);
		 when(urlService.returnDateNow()).thenReturn(dateTest);
		 assertThat(urlService.findRedirect(SHORT_URL_B2C3).getStatusCodeValue(), is(410));
	 }
	
	@Test
	public void redirectFail() throws Exception {
		final Url url = new Url(1L, ULR_GLOBO, SHOR_URL_SHORT, LocalDateTime.of(2020, 07, 01, 01, 50, 56));	
		 when(urlRepository.findByShortUrl(SHORT_URL_B2C3)).thenReturn(url);
		 assertThat(urlService.findRedirect("fail").getStatusCodeValue(), is(404));
	 }
}
