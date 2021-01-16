package com.gmdb.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class GmdbControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Test
	public void testGetMovieTitles_ReturnsEmptyList() throws Exception {
		mockMvc.perform(get("/api/movies"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.movieTitles").isEmpty());
	}
	
}
