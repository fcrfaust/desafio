package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Url;

public interface Urls extends JpaRepository<Url,Long>{

	 	@Query("FROM Url WHERE urlShort = ?1")
	    Url findByShortUrl(String shorturl);
	
}
