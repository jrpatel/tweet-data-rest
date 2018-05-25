package com.mit.tweets.exception;

public class TweetExceptionResponse {
	
	private String code;
	private String errorMessage;
	
	public TweetExceptionResponse() {
		
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

}
