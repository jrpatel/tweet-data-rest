package com.mit.tweets.exception;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.client.ResponseErrorHandler;

import com.mit.tweets.service.TweetServiceImpl;


public class ErrorHandler implements ResponseErrorHandler {

	private static final Logger logger = LoggerFactory.getLogger(TweetServiceImpl.class);
	@Override
	public void handleError(ClientHttpResponse httpResponse) throws IOException {
		// TODO Auto-generated method stub
	
		if(httpResponse.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
			System.out.println("is it even coming here???");
			logger.debug("Throwing internal error {}",HttpStatus.INTERNAL_SERVER_ERROR);
			throw new HttpMessageNotWritableException("Server is down");
		}
		else if(httpResponse.getStatusCode() != HttpStatus.OK) {
			logger.debug("Bad Resourse not found");
			throw new HttpMessageNotWritableException("Resource not found or Bad request");
			
		}
		
	}
	@Override
	public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
		// TODO Auto-generated method stub
		return true;
	}

}
