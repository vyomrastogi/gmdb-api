package com.gmdb.util;

import com.gmdb.model.Movie;

public class TestHelper {

    public static Movie movieContent(){
        return Movie.builder().title("Avatar").director("James Cameron").actors("Sam Worthington, Zoe Salanada, " +
                "Stephen Lang").releaseYear("2009")
                .description("A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn " +
                        "between following his orders and protecting the world he feels is his home.")
                .build();
    }
}
