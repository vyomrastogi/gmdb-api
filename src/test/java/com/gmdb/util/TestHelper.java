package com.gmdb.util;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmdb.model.Movie;
import com.gmdb.model.MovieReview;
import com.gmdb.response.MovieDetail;
import com.gmdb.response.Review;

public class TestHelper {

    public static Movie movieContent(){
        return Movie.builder().title("Avatar").director("James Cameron").actors("Sam Worthington, Zoe Salanada, " +
                "Stephen Lang").releaseYear("2009")
                .description("A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn " +
                        "between following his orders and protecting the world he feels is his home.")
                .build();
    }
    
    public static MovieDetail movieDetailContentWithoutRating(){       
        return new MovieDetail(movieContent());
        
    }
    
    public static MovieDetail movieDetailContent(){
        MovieDetail detail =  new MovieDetail(movieContent());
        
        List<Review> reviews = new ArrayList<>();
        reviews.add(Review.builder().rating(5).build());
        
        detail.setRating(5.0);
        detail.setReviews(reviews);
        return detail;
        
    }
    
    public static MovieDetail movieDetailContentWithMultipleReview(){
        MovieDetail detail =  new MovieDetail(movieContent());
        
        List<Review> reviews = new ArrayList<>();
        reviews.add(Review.builder().rating(5).build());
        reviews.add(Review.builder().rating(3).build());
        detail.setRating(4.0);
        detail.setReviews(reviews);
        return detail;
        
    }
    
    public static List<MovieReview> movieReviews(){
    	List<MovieReview> reviews = new ArrayList<>();
    	reviews.add(MovieReview.builder().rating(5).build());
    	reviews.add(MovieReview.builder().rating(3).build());
    	return reviews;
    }
    
    public static List<MovieReview> movieReview(){
    	List<MovieReview> reviews = new ArrayList<>();
    	reviews.add(MovieReview.builder().rating(5).build());
    	return reviews;
    }
    
    public static String reviewContent() throws JsonProcessingException {
    	ObjectMapper mapper = new ObjectMapper();
    	return mapper.writeValueAsString(Review.builder().rating(5).build());
    }
}
