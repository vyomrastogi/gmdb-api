package com.gmdb.service;

import java.util.stream.Collectors;

import com.gmdb.response.MovieDetailResponse;
import org.springframework.stereotype.Service;

import com.gmdb.model.Movie;
import com.gmdb.repository.GmdbRepository;
import com.gmdb.response.MovieTitlesResponse;

@Service
public class GmdbService {

	GmdbRepository repository;

	public GmdbService(GmdbRepository repository) {
		this.repository = repository;
	}

	public MovieTitlesResponse getMovieTitles() {
		return MovieTitlesResponse.builder()
				.movieTitles(
						repository.findAll()
						.stream()
						.map(Movie::getTitle)
						.collect(Collectors.toList()))
				.build();
	}

    public MovieDetailResponse getMovieDetail(String movieTitle) {
		return MovieDetailResponse.builder()
				.movieDetail(repository.findById(movieTitle).get()).build();
    }
}