package com.gmdb.controller;

import com.gmdb.exception.MovieNotFoundException;
import com.gmdb.model.Movie;
import com.gmdb.repository.GmdbRepository;
import com.gmdb.util.TestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@Transactional
public class GmdbControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GmdbRepository repository;

    @Test
    public void testGetMovieTitles_ReturnsEmptyList() throws Exception {
        mockMvc.perform(get("/api/movies")).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.movieTitles").isEmpty())
                .andExpect(jsonPath("$.errorMessages").isEmpty());
    }

    @Test
    public void testGetMovieTitles_ReturnsNonEmptyList() throws Exception {
        repository.save(new Movie("Batman"));
        mockMvc.perform(get("/api/movies")).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.movieTitles.length()").value(1))
                .andExpect(jsonPath("$.errorMessages").isEmpty());
    }

    @Test
    public void testGetMovieDetail_ReturnsMovieDetail() throws Exception {
        Movie expectedMovie = TestHelper.movieContent();
        repository.save(expectedMovie);

        mockMvc.perform(get("/api/movies/{title}","Avatar")).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.movieDetail").exists())
                .andExpect(jsonPath("$.data.movieDetail.title").value(expectedMovie.getTitle()))
                .andExpect(jsonPath("$.data.movieDetail.director").value(expectedMovie.getDirector()))
                .andExpect(jsonPath("$.data.movieDetail.actors").value(expectedMovie.getActors()))
                .andExpect(jsonPath("$.data.movieDetail.releaseYear").value(expectedMovie.getReleaseYear()))
                .andExpect(jsonPath("$.data.movieDetail.description").value(expectedMovie.getDescription()))
                .andExpect(jsonPath("$.data.movieDetail.rating").value(expectedMovie.getRating()))
                .andExpect(jsonPath("$.errorMessages").isEmpty());
    }

    @Test
    public void testGetMovieDetails_ReturnsMovieNotFound() throws Exception {
        mockMvc.perform(get("/api/movies/{title}","Dummy"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.errorMessages.length()").value(1))
                .andExpect(jsonPath("$.errorMessages[0]").value("Movie - {Dummy} not found"));
    }

}
