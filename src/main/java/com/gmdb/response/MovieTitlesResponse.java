package com.gmdb.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MovieTitlesResponse {

	@Builder.Default
	private List<String> movieTitles = new ArrayList<>();
}
