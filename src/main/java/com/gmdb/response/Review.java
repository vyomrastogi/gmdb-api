package com.gmdb.response;

import com.gmdb.model.MovieReview;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {
	private Integer rating;
	private String review;

	public Review(MovieReview review) {
		this.rating = review.getRating();
		this.review = review.getReview();
	}
}
