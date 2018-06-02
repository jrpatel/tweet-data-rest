package com.mit.tweets.exception;

public class TweetException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TweetException() {}
	//Constructor that accepts exception message
	public TweetException(String message) {	
		super(message);	
	}
}
