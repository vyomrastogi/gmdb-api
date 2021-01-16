package com.gmdb.exception;

public class MovieNotFoundException extends Exception{

    public MovieNotFoundException(String title){
        super(String.format("Movie - {%s} not found", title));
    }
}
