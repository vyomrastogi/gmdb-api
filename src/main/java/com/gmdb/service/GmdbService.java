package com.gmdb.service;

import java.util.List;
import java.util.stream.Collectors;

import com.gmdb.exception.MovieNotFoundException;
import com.gmdb.response.MovieDetail;
import com.gmdb.response.MovieDetailResponse;
import org.springframework.stereotype.Service;

import com.gmdb.model.Movie;
import com.gmdb.model.MovieReview;
import com.gmdb.repository.GmdbRepository;
import com.gmdb.repository.GmdbReviewRepository;
import com.gmdb.response.MovieTitlesResponse;
import com.gmdb.response.Review;

@Service
public class GmdbService {

	GmdbRepository repository;
	GmdbReviewRepository reviewRepository;

	public GmdbService(GmdbRepository repository, GmdbReviewRepository reviewRepository) {
		this.repository = repository;
		this.reviewRepository = reviewRepository;
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

    public MovieDetailResponse getMovieDetail(String movieTitle) throws MovieNotFoundException {
		return MovieDetailResponse.builder()
				.movieDetail(repository.findById(movieTitle).map(MovieDetail::new)
						.orElseThrow(()->new MovieNotFoundException(movieTitle))).build();
    }

	public MovieDetailResponse addMovieReview(String title, Review review) throws MovieNotFoundException {
		Movie movie = repository.findById(title).orElseThrow(()->new MovieNotFoundException(title));
		MovieReview savedReview = reviewRepository.save(new MovieReview(title, review));
		List<MovieReview> reviews = reviewRepository.findAllByTitle(title);
		Double rating = reviews.stream().mapToInt(MovieReview::getRating).average().getAsDouble();
		List<Review> savedReviews = reviews.stream().map(Review::new).collect(Collectors.toList());
		return MovieDetailResponse.builder().movieDetail(new MovieDetail(movie,rating,savedReviews)).build();
	}
}