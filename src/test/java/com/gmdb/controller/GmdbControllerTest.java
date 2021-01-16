package com.gmdb.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
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

}
