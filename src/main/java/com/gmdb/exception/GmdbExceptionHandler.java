package com.gmdb.exception;

import com.gmdb.response.GmdbBaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GmdbExceptionHandler {

    @ExceptionHandler(MovieNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GmdbBaseResponse handleMovieNotFoundException(MovieNotFoundException exception){
        GmdbBaseResponse response = new GmdbBaseResponse();
        response.appendErrorMessage(exception.getMessage());
        return response;
    }
    
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public GmdbBaseResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		GmdbBaseResponse response = new GmdbBaseResponse();
		exception.getAllErrors().forEach(e -> response.appendErrorMessage(e.getDefaultMessage()));
		return response;
	}


}
