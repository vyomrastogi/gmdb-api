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
import com.gmdb.response.MovieDetailResponse;
import com.gmdb.util.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gmdb.model.Movie;
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

	@Test
	public void testGetMovieTitles_ReturnsMovieTitleResponse_WithTitles() {

		when(repository.findAll()).thenReturn(Stream
				.of(new Movie("Avengers"),
						new Movie("Avatar"),
						new Movie("Titanic")).collect(Collectors.toList()));

		MovieTitlesResponse actualResponse = service.getMovieTitles();
		assertNotNull(actualResponse);
		assertThat(actualResponse.getMovieTitles()).hasSize(3);

		verify(repository).findAll();
	}

	@Test
	public void testGetMovieDetails_ReturnsMovieDetailResponse() throws MovieNotFoundException {
		when(repository.findById("Avatar")).thenReturn(Optional.of(TestHelper.movieContent()));
		MovieDetailResponse actualResponse = service.getMovieDetail("Avatar");
		assertNotNull(actualResponse);
		assertThat(actualResponse.getMovieDetail()).extracting("title","director","actors","releaseYear"
				,"description","rating").containsExactly("Avatar","James Cameron","Sam Worthington, Zoe Salanada, Stephen Lang","2009"
				,"A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home."
				,null);

		verify(repository).findById("Avatar");
	}

	@Test
	public void testGetMovieDetails_ReturnsMovieNotFound() {
		when(repository.findById("Dummy")).thenReturn(Optional.empty());
		MovieNotFoundException movieNotFoundException = assertThrows(MovieNotFoundException.class, () -> service.getMovieDetail("Dummy"));
		assertEquals("Movie - {Dummy} not found", movieNotFoundException.getMessage());
		verify(repository).findById("Dummy");
	}


}
