package com.gmdb.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Review {
	private Integer rating;
	private String review;

}
