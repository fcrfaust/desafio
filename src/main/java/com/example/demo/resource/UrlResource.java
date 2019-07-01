package com.example.demo.resource;

import java.net.URI;
import java.net.URISyntaxException;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Url;
import com.example.demo.repository.Urls;

import net.bytebuddy.utility.RandomString;

@RestController
public class UrlResource {
	private static final String LOCALHOST = "http://localhost:8080/";
	
	@Autowired
	private Urls urls;	
	
	@PostMapping("/url")
	public Url addUrl(@Valid @RequestBody Url url) {
		url.setExpiryDate(createExpiryDate());
		url.setUrlShort(RandomString.make(32));
		return urls.save(url);
	}
	
	@GetMapping("/url")
	public List<Url> listUrl() {
		return urls.findAll();
	}
	
	@RequestMapping(value="/{shortUrl}", method=RequestMethod.GET)
	public ResponseEntity<Url> find(@PathVariable String shortUrl, HttpServletResponse httpServletResponse) throws URISyntaxException{
		Url url = urls.findByShortUrl(shortUrl);
		if (url == null) 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

		if (url.getExpiryDate().compareTo(Calendar.getInstance().getTime()) < 0)
			return ResponseEntity.status(HttpStatus.GONE).body(null);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", url.getUrlOriginal());    
		return new ResponseEntity(headers,HttpStatus.FOUND);
	}
	
	public Date createExpiryDate() {
	    Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.HOUR_OF_DAY, 1);
	    return calendar.getTime();
	}
	
	public String createShortUrl() {
		String uuid = UUID.randomUUID().toString();
        return uuid;
	    
	}
	
	
}
