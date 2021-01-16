package com.gmdb.controller;

import com.gmdb.model.Movie;
import com.gmdb.repository.GmdbRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

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

}
