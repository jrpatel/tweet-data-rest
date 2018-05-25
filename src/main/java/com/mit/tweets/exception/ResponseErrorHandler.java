package com.mit.tweets.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
@EnableWebMvc
public class ResponseErrorHandler {
	
	@ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class,HttpClientErrorException.class,ResourceAccessException.class})
	protected ResponseEntity<TweetExceptionResponse> handleBadRequestException(RuntimeException ex, WebRequest request) {
		TweetExceptionResponse response = new TweetExceptionResponse();
		response.setErrorMessage("could not complete request!! Resource Unavailable or Could not find");
		response.setCode("Not_found");
        return new ResponseEntity<TweetExceptionResponse>(response,HttpStatus.NOT_FOUND);
    }

	@ExceptionHandler(TweetException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected  ResponseEntity<TweetExceptionResponse> handleExceptions(Exception ex, WebRequest request) {
		System.out.println("second exception");
		TweetExceptionResponse response = new TweetExceptionResponse();
		response.setErrorMessage(ex.getMessage());
		response.setCode("Internal_Error");
        return new ResponseEntity<TweetExceptionResponse>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
