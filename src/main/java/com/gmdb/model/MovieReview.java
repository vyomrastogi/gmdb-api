package com.gmdb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.gmdb.response.Review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class MovieReview {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String title;
	private Integer rating;
	private String review;
	
	public MovieReview(String title, Review review) {
		this.title = title;
		this.rating = review.getRating();
		this.review = null == review.getReview() ? "" : review.getReview();
	}

	public MovieReview() {
	}
	
}
