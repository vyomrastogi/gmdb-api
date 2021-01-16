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
				.movieTitles(repository.findAll().stream().map(Movie::getTitle).collect(Collectors.toList())).build();
	}

	public MovieDetailResponse getMovieDetail(String movieTitle) throws MovieNotFoundException {
		Movie movie = getMovieByTitle(movieTitle);
		return MovieDetailResponse.builder().movieDetail(new MovieDetail(movie)).build();
	}

	/**
	 * Adds given review to movie title and returns movie detail with average rating and all review contributions
	 * 
	 * @param title
	 * @param review
	 * @return
	 * @throws MovieNotFoundException
	 */
	public MovieDetailResponse addMovieReview(String title, Review review) throws MovieNotFoundException {
		Movie movie = getMovieByTitle(title);
		MovieReview savedReview = reviewRepository.save(new MovieReview(title, review));
		List<MovieReview> reviews = reviewRepository.findAllByTitle(title);
		Double rating = reviews.stream().mapToInt(MovieReview::getRating).average().getAsDouble();
		List<Review> savedReviews = reviews.stream().map(Review::new).collect(Collectors.toList());
		return MovieDetailResponse.builder().movieDetail(new MovieDetail(movie, rating, savedReviews)).build();
	}

	private Movie getMovieByTitle(String title) throws MovieNotFoundException {
		return repository.findById(title).orElseThrow(() -> new MovieNotFoundException(title));
	}
}