package com.gmdb.controller;

import com.gmdb.exception.MovieNotFoundException;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmdb.response.GmdbBaseResponse;
import com.gmdb.response.Review;
import com.gmdb.service.GmdbService;

@RestController
@RequestMapping("/api")
@Validated
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
	
	@PostMapping("/movies/{title}/review")
	public GmdbBaseResponse addReview(@PathVariable String title, @Valid @RequestBody Review review) throws MovieNotFoundException {
		return new GmdbBaseResponse(service.addMovieReview(title,review));
	}
}