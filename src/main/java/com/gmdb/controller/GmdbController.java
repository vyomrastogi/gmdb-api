package com.gmdb.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmdb.response.GmdbBaseResponse;
import com.gmdb.response.MovieTitlesResponse;

@RestController
@RequestMapping("/api")
public class GmdbController {

	@GetMapping("/movies")
	public GmdbBaseResponse getMovieTitles() {
		GmdbBaseResponse response = new GmdbBaseResponse();
		MovieTitlesResponse internalResponse = new MovieTitlesResponse();
		internalResponse.setMovieTitles(new ArrayList<>());
		response.setData(internalResponse);
		return response;
	}
}