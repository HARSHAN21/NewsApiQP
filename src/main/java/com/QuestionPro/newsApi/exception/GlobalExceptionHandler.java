package com.QuestionPro.newsApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

// This is global exception handler class to handle all exceptions occurred in this project
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = HttpClientErrorException.class)
	public ResponseEntity<Object> handleException(HttpClientErrorException exception){
		return new ResponseEntity<Object>("Request Data not found!,please try again later!", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> handleException(Exception exception){
		return new ResponseEntity<Object>("Some problem occured!, please try again later!", HttpStatus.NOT_FOUND);
	}

}
