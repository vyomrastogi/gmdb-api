package com.gmdb.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gmdb.repository.GmdbRepository;
import com.gmdb.response.MovieTitlesResponse;

public class GmdbServiceTest {
	
	GmdbRepository repository;
	GmdbService service;
	
	@BeforeEach
	public void init() {
		repository = mock(GmdbRepository.class);
		service = new GmdbService(repository);
	}
	
	@Test
	public void testGetMovieTitles_ReturnsMovieTitleResponse() {
		when(repository.findAll()).thenReturn(new ArrayList<>());
		
		MovieTitlesResponse actualResponse = service.getMovieTitles();
		assertNotNull(actualResponse);
		assertThat(actualResponse.getMovieTitles()).hasSize(0);
		
		verify(repository).findAll();
	}

}
