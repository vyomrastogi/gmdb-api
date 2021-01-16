package com.gmdb.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import com.gmdb.exception.MovieNotFoundException;
import com.gmdb.model.Movie;
import com.gmdb.response.MovieDetailResponse;
import com.gmdb.util.TestHelper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.gmdb.response.MovieTitlesResponse;
import com.gmdb.service.GmdbService;

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
				.movieDetail(TestHelper.movieContent()).build());
		mockMvc.perform(get("/api/movies/{title}","Avatar")).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.movieDetail").exists())
				.andExpect(jsonPath("$.data.movieDetail.title").value(expectedMovie.getTitle()))
				.andExpect(jsonPath("$.data.movieDetail.director").value(expectedMovie.getDirector()))
				.andExpect(jsonPath("$.data.movieDetail.actors").value(expectedMovie.getActors()))
				.andExpect(jsonPath("$.data.movieDetail.releaseYear").value(expectedMovie.getReleaseYear()))
				.andExpect(jsonPath("$.data.movieDetail.description").value(expectedMovie.getDescription()))
				.andExpect(jsonPath("$.data.movieDetail.rating").value(expectedMovie.getRating()))
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
}
