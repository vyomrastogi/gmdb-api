package com.gmdb.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.gmdb.exception.MovieNotFoundException;
import com.gmdb.response.MovieDetail;
import com.gmdb.response.MovieDetailResponse;
import com.gmdb.util.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.gmdb.model.Movie;
import com.gmdb.model.MovieReview;
import com.gmdb.repository.GmdbRepository;
import com.gmdb.repository.GmdbReviewRepository;
import com.gmdb.response.MovieTitlesResponse;
import com.gmdb.response.Review;

public class GmdbServiceTest {

	GmdbRepository movieRepository;
	GmdbService service;
	GmdbReviewRepository reviewRepository;

	@BeforeEach
	public void init() {
		movieRepository = mock(GmdbRepository.class);
		reviewRepository = mock(GmdbReviewRepository.class);
		service = new GmdbService(movieRepository, reviewRepository);
	}

	@Test
	public void testGetMovieTitles_ReturnsMovieTitleResponse() {
		when(movieRepository.findAll()).thenReturn(new ArrayList<>());

		MovieTitlesResponse actualResponse = service.getMovieTitles();
		assertNotNull(actualResponse);
		assertThat(actualResponse.getMovieTitles()).hasSize(0);

		verify(movieRepository).findAll();
	}

	@Test
	public void testGetMovieTitles_ReturnsMovieTitleResponse_WithTitles() {

		when(movieRepository.findAll()).thenReturn(Stream
				.of(new Movie("Avengers"), new Movie("Avatar"), new Movie("Titanic")).collect(Collectors.toList()));

		MovieTitlesResponse actualResponse = service.getMovieTitles();
		assertNotNull(actualResponse);
		assertThat(actualResponse.getMovieTitles()).hasSize(3);

		verify(movieRepository).findAll();
	}

	@Test
	public void testGetMovieDetails_ReturnsMovieDetailResponse() throws MovieNotFoundException {
		when(movieRepository.findById("Avatar")).thenReturn(Optional.of(TestHelper.movieContent()));
		MovieDetailResponse actualResponse = service.getMovieDetail("Avatar");
		assertNotNull(actualResponse);
		assertThat(actualResponse.getMovieDetail())
				.extracting("title", "director", "actors", "releaseYear", "description", "rating")
				.containsExactly("Avatar", "James Cameron", "Sam Worthington, Zoe Salanada, Stephen Lang", "2009",
						"A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
						null);

		verify(movieRepository).findById("Avatar");
	}

	@Test
	public void testGetMovieDetails_ReturnsMovieNotFound() {
		when(movieRepository.findById("Dummy")).thenReturn(Optional.empty());
		MovieNotFoundException movieNotFoundException = assertThrows(MovieNotFoundException.class,
				() -> service.getMovieDetail("Dummy"));
		assertEquals("Movie - {Dummy} not found", movieNotFoundException.getMessage());
		verify(movieRepository).findById("Dummy");
	}

	@Test
	public void testAddMovieReview_withOnlyRating() throws MovieNotFoundException {
		
		when(reviewRepository.save(Mockito.any(MovieReview.class))).thenReturn(new MovieReview());
		when(movieRepository.findById("Avatar")).thenReturn(Optional.of(TestHelper.movieContent()));
		
		MovieDetailResponse actualResponse = service.addMovieReview("Avatar",Review.builder().rating(5).build());
		
		assertNotNull(actualResponse);		

		assertThat(actualResponse.getMovieDetail())
				.extracting("title", "director", "actors", "releaseYear", "description", "rating")
				.containsExactly("Avatar", "James Cameron", "Sam Worthington, Zoe Salanada, Stephen Lang", "2009",
						"A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
						5);
		assertThat(actualResponse.getMovieDetail().getReviews()).hasSize(1);
		
		verify(reviewRepository).save(Mockito.any(MovieReview.class));
		verify(movieRepository).findById("Avatar");
	}

}
