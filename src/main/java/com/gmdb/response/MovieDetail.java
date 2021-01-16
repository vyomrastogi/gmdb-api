package com.gmdb.response;

import java.util.ArrayList;
import java.util.List;

import com.gmdb.model.Movie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDetail {
	
	private String title;
	private String director;
	private String actors;
	private String releaseYear;
	private String description;
	private Double rating;
	private List<Review> reviews = new ArrayList<>();
	
	public MovieDetail(Movie movie) {
		this.title = movie.getTitle();
		this.description = movie.getDescription();
		this.director = movie.getDirector();		
		this.releaseYear = movie.getReleaseYear();
		this.actors = movie.getActors();
	}

	public MovieDetail(Movie movie,Double rating, List<Review> reviews) {
		this(movie);
		String str = String.format("%1.1f", rating);
		this.rating = Double.valueOf(str);
		this.reviews = reviews;
		
	}
}
