package com.example.demo.resource;

import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Url;
import com.example.demo.service.UrlService;

@RestController
public class UrlResource {
	
	@Autowired
	private UrlService urlService;
	
	@PostMapping("/url")
	public Url addUrl(@Valid @RequestBody Url url) {;
		return urlService.addUrl(url);
	}
	
	@GetMapping("/url")
	public List<Url> listUrl() {
		return urlService.findAll();
		
	}
	
	@RequestMapping(value="/{shortUrl}", method=RequestMethod.GET)
	public ResponseEntity<Url> find(@PathVariable String shortUrl, HttpServletResponse httpServletResponse) throws URISyntaxException{	
		return urlService.findRedirect(shortUrl,httpServletResponse);
	}
	

	
	
}
