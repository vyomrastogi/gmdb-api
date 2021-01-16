package com.gmdb.response;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

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
	
	private static final String MESSAGE = "Rating is required to submit review. Value can be any number between 0 & 5";
	
	@PositiveOrZero(message = MESSAGE)
	@Max(value = 5, message = MESSAGE)
	@NotNull(message = MESSAGE)
	private Integer rating;
	private String review;

	public Review(MovieReview review) {
		this.rating = review.getRating();
		this.review = review.getReview();
	}
}
