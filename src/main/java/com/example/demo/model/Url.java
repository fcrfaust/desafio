package com.example.demo.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class Url {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String urlOriginal;
	
	@Column(unique=true)
	private String urlShort;	
	
	
	@DateTimeFormat
	private Date expiryDate;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrlOriginal() {
		return urlOriginal;
	}

	public void setUrlOriginal(String urlOriginal) {
		this.urlOriginal = urlOriginal;
	}

	public String getUrlShort() {
		return urlShort;
	}

	public void setUrlShort(String urlShort) {
		this.urlShort = urlShort;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}
	
	public void setExpiryDate(Date expireDate) {
		this.expiryDate = expireDate;
	}

	
}
