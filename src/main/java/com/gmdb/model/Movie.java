package com.gmdb.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Movie {

	@Id
	private String title;

	public Movie() {

	}
	
	public Movie(String title) {
		this.title = title;
	}

}
