package com.gmdb.exception;

public class MovieNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public MovieNotFoundException(String title){
        super(String.format("Movie - {%s} not found", title));
    }
}
