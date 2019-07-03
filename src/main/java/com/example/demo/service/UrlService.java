package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.model.Url;
import com.example.demo.repository.Urls;

import net.bytebuddy.utility.RandomString;

@Service
@Transactional
public class UrlService {

	@Autowired
	private Urls repository;
	
	public Url addUrl(Url url) {
		url.setExpiryDate(createExpiryDate());
		url.setUrlShort(RandomString.make(32));
		return repository.save(url);
	}
	
	public LocalDateTime createExpiryDate() {
		LocalDateTime calendar = LocalDateTime.now().plusHours(1L);
	    return calendar;
	}
	
	public String createShortUrl() {
		String uuid = UUID.randomUUID().toString();
        return uuid;
	    
	}

	public List<Url> findAll() {
		return repository.findAll();
	}

	public ResponseEntity<Url> findRedirect(String shortUrl, HttpServletResponse httpServletResponse) {
		Url url = repository.findByShortUrl(shortUrl);
		
		if (url == null) 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

		if (url.getExpiryDate().compareTo(LocalDateTime.now()) < 0)
			return ResponseEntity.status(HttpStatus.GONE).body(null);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", url.getUrlOriginal());    
		return new ResponseEntity(headers,HttpStatus.FOUND);
	}
	
}
