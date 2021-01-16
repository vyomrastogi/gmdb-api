package com.gmdb.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@AllArgsConstructor
public class Movie {

	@Id
	private String title;

	private String director;
	private String actors;
	private String releaseYear;
	private String description;
	private Double rating;

	public Movie() {

	}
	
	public Movie(String title) {
		this.title = title;
	}

}
