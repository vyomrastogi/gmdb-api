package com.gmdb.response;

import com.gmdb.model.Movie;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MovieDetailResponse {
    private Movie movieDetail;
}
