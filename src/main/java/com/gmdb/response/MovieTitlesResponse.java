package com.gmdb.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieTitlesResponse {

	private List<String> movieTitles = new ArrayList<>();
}
