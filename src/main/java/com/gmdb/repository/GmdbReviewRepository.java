package com.gmdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmdb.model.MovieReview;

public interface GmdbReviewRepository extends  JpaRepository<MovieReview, Integer>{

}
