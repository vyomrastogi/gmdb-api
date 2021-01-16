package com.gmdb.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.gmdb.model.Movie;
import com.gmdb.model.MovieReview;
import com.gmdb.repository.GmdbRepository;
import com.gmdb.repository.GmdbReviewRepository;
import com.gmdb.response.MovieDetail;
import com.gmdb.util.TestHelper;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@Transactional
public class GmdbControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GmdbRepository repository;
    
    @Autowired
    private GmdbReviewRepository GmdbReviewRepository;

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
                .andExpect(jsonPath("$.data.movieDetail.rating").isEmpty())
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
    
    @Test
	public void testAddReview() throws Exception {
		
		MovieDetail expectedMovie = TestHelper.movieDetailContent();
		Movie expectedMovieContent = TestHelper.movieContent();
	        repository.save(expectedMovieContent);
		
		mockMvc.perform(post("/api/movies/{title}/review","Avatar").content(TestHelper.reviewContent()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").exists())
				.andExpect(jsonPath("$.data.movieDetail.title").value(expectedMovie.getTitle()))
				.andExpect(jsonPath("$.data.movieDetail.director").value(expectedMovie.getDirector()))
				.andExpect(jsonPath("$.data.movieDetail.actors").value(expectedMovie.getActors()))
				.andExpect(jsonPath("$.data.movieDetail.releaseYear").value(expectedMovie.getReleaseYear()))
				.andExpect(jsonPath("$.data.movieDetail.description").value(expectedMovie.getDescription()))
				.andExpect(jsonPath("$.data.movieDetail.rating").value(5.0))
				.andExpect(jsonPath("$.data.movieDetail.reviews.length()").value(1))
				.andExpect(jsonPath("$.errorMessages").isEmpty());

	}
    
    @Test
	public void testAddReviewWithTextAndMultipleReviews() throws Exception {
		
		MovieDetail expectedMovie = TestHelper.movieDetailContent();
		Movie expectedMovieContent = TestHelper.movieContent();
	        repository.save(expectedMovieContent);
	    
	    GmdbReviewRepository.save(MovieReview.builder().title("Avatar").rating(5).review("Review text").build());
		
		mockMvc.perform(post("/api/movies/{title}/review","Avatar").content(TestHelper.reviewContent()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").exists())
				.andExpect(jsonPath("$.data.movieDetail.title").value(expectedMovie.getTitle()))
				.andExpect(jsonPath("$.data.movieDetail.director").value(expectedMovie.getDirector()))
				.andExpect(jsonPath("$.data.movieDetail.actors").value(expectedMovie.getActors()))
				.andExpect(jsonPath("$.data.movieDetail.releaseYear").value(expectedMovie.getReleaseYear()))
				.andExpect(jsonPath("$.data.movieDetail.description").value(expectedMovie.getDescription()))
				.andExpect(jsonPath("$.data.movieDetail.rating").value(5.0))
				.andExpect(jsonPath("$.data.movieDetail.reviews.length()").value(2))
				.andExpect(jsonPath("$.errorMessages").isEmpty());
	}

}
