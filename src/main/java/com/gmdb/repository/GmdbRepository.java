package com.gmdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmdb.model.Movie;

public interface GmdbRepository extends JpaRepository<Movie, String> {

}
