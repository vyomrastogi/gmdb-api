package com.gmdb.controller;

import com.gmdb.exception.MovieNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmdb.response.GmdbBaseResponse;
import com.gmdb.service.GmdbService;

@RestController
@RequestMapping("/api")
public class GmdbController {

	GmdbService service;

	public GmdbController(GmdbService service) {
		this.service = service;
	}

	@GetMapping("/movies")
	public GmdbBaseResponse getMovieTitles() {
		return new GmdbBaseResponse(service.getMovieTitles());
	}

	@GetMapping("/movies/{title}")
	public GmdbBaseResponse getMovieDetail(@PathVariable String title) throws MovieNotFoundException {
		return new GmdbBaseResponse(service.getMovieDetail(title));
	}
}