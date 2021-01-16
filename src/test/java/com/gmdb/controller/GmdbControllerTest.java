package com.gmdb.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.gmdb.exception.MovieNotFoundException;
import com.gmdb.model.Movie;
import com.gmdb.response.MovieDetail;
import com.gmdb.response.MovieDetailResponse;
import com.gmdb.response.MovieTitlesResponse;
import com.gmdb.response.Review;
import com.gmdb.service.GmdbService;
import com.gmdb.util.TestHelper;

@WebMvcTest
public class GmdbControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	GmdbService service;

	@Test
	public void testGetMovieTitles_ReturnsEmptyList() throws Exception {
		when(service.getMovieTitles()).thenReturn(MovieTitlesResponse.builder().build());

		mockMvc.perform(get("/api/movies")).andExpect(status().isOk())
		.andExpect(jsonPath("$.data.movieTitles").isEmpty())
		.andExpect(jsonPath("$.errorMessages").isEmpty());

		verify(service).getMovieTitles();
	}
	
	@Test
	public void testGetMovieTitles_ReturnsNonEmptyList() throws Exception {
		
		when(service.getMovieTitles()).thenReturn(MovieTitlesResponse.builder()
				.movieTitles(Arrays.asList("Avengers","Avatar","Wonder Woman 1984"))
				.build());

		mockMvc.perform(get("/api/movies")).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.movieTitles.length()").value(3))
				.andExpect(jsonPath("$.errorMessages").isEmpty());

		verify(service).getMovieTitles();
	}

	@Test
	public void testGetMovieDetail_ReturnsMovieDetail() throws Exception {

		Movie expectedMovie = TestHelper.movieContent();

		when(service.getMovieDetail(Mockito.anyString())).thenReturn(MovieDetailResponse.builder()
				.movieDetail(TestHelper.movieDetailContentWithoutRating()).build());
		mockMvc.perform(get("/api/movies/{title}","Avatar")).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.movieDetail").exists())
				.andExpect(jsonPath("$.data.movieDetail.title").value(expectedMovie.getTitle()))
				.andExpect(jsonPath("$.data.movieDetail.director").value(expectedMovie.getDirector()))
				.andExpect(jsonPath("$.data.movieDetail.actors").value(expectedMovie.getActors()))
				.andExpect(jsonPath("$.data.movieDetail.releaseYear").value(expectedMovie.getReleaseYear()))
				.andExpect(jsonPath("$.data.movieDetail.description").value(expectedMovie.getDescription()))
				.andExpect(jsonPath("$.data.movieDetail.rating").isEmpty())
				.andExpect(jsonPath("$.errorMessages").isEmpty());

		verify(service).getMovieDetail(Mockito.anyString());
	}

	@Test
	public void testGetMovieDetails_ReturnsMovieNotFound() throws Exception {
		when(service.getMovieDetail("Dummy")).thenThrow(new MovieNotFoundException("Dummy"));

		mockMvc.perform(get("/api/movies/{title}","Dummy"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.errorMessages.length()").value(1))
				.andExpect(jsonPath("$.errorMessages[0]").value("Movie - {Dummy} not found"));

		verify(service).getMovieDetail("Dummy");
	}
	
	@Test
	public void testAddReview() throws Exception {

		MovieDetail expectedMovie = TestHelper.movieDetailContent();
		when(service.addMovieReview(Mockito.anyString(), Mockito.any(Review.class)))
				.thenReturn(MovieDetailResponse.builder().movieDetail(expectedMovie).build());

		mockMvc.perform(post("/api/movies/{title}/review", "Avatar").content(TestHelper.reviewContent())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data").exists())
				.andExpect(jsonPath("$.data.movieDetail.title").value(expectedMovie.getTitle()))
				.andExpect(jsonPath("$.data.movieDetail.director").value(expectedMovie.getDirector()))
				.andExpect(jsonPath("$.data.movieDetail.actors").value(expectedMovie.getActors()))
				.andExpect(jsonPath("$.data.movieDetail.releaseYear").value(expectedMovie.getReleaseYear()))
				.andExpect(jsonPath("$.data.movieDetail.description").value(expectedMovie.getDescription()))
				.andExpect(jsonPath("$.data.movieDetail.rating").value(5))
				.andExpect(jsonPath("$.data.movieDetail.reviews.length()").value(1))
				.andExpect(jsonPath("$.errorMessages").isEmpty());

		verify(service).addMovieReview(Mockito.anyString(), Mockito.any(Review.class));
	}
	
	
	@Test
	public void testAddReviewWithTextAndMultipleReviews() throws Exception {

		MovieDetail expectedMovie = TestHelper.movieDetailContentWithMultipleReview();
		when(service.addMovieReview(Mockito.anyString(), Mockito.any(Review.class)))
				.thenReturn(MovieDetailResponse.builder().movieDetail(expectedMovie).build());

		mockMvc.perform(post("/api/movies/{title}/review", "Avatar").content(TestHelper.reviewContent())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data").exists())
				.andExpect(jsonPath("$.data.movieDetail.title").value(expectedMovie.getTitle()))
				.andExpect(jsonPath("$.data.movieDetail.director").value(expectedMovie.getDirector()))
				.andExpect(jsonPath("$.data.movieDetail.actors").value(expectedMovie.getActors()))
				.andExpect(jsonPath("$.data.movieDetail.releaseYear").value(expectedMovie.getReleaseYear()))
				.andExpect(jsonPath("$.data.movieDetail.description").value(expectedMovie.getDescription()))
				.andExpect(jsonPath("$.data.movieDetail.rating").value(4.0))
				.andExpect(jsonPath("$.data.movieDetail.reviews.length()").value(2))
				.andExpect(jsonPath("$.errorMessages").isEmpty());
		verify(service).addMovieReview(Mockito.anyString(), Mockito.any(Review.class));
	}
	
	@Test
	public void testAddMovieDetails_ReturnsMovieNotFound() throws Exception {
		when(service.addMovieReview(Mockito.anyString(), Mockito.any(Review.class))).thenThrow(new MovieNotFoundException("Dummy"));

		mockMvc.perform(post("/api/movies/{title}/review", "Dummy").content(TestHelper.reviewContent())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.errorMessages.length()").value(1))
				.andExpect(jsonPath("$.errorMessages[0]").value("Movie - {Dummy} not found"));

		verify(service).addMovieReview(Mockito.anyString(), Mockito.any(Review.class));
	}

}
