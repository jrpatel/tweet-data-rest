package com.mit.tweets.service;

import java.util.List;

import com.mit.tweets.exception.TweetException;
import com.mit.tweets.model.Response;
import com.mit.tweets.model.Tweet;
import com.mit.tweets.model.WeatherResponse;
import com.mit.tweets.model.Woeid;

/*
 * this interface provides blueprint methods for required methods 
 * for TweetService.
 */

public interface TweetService {
	
	public Tweet[] getTweets() throws TweetException;
	public List<Response> getWeatherResponse(Tweet[] tweets) throws TweetException;
	public Woeid getWhereonEarth(String lonn, String latt);
	public WeatherResponse getWeatherDetails(Woeid woeid);
	public Response getResponse(Tweet tweet) throws TweetException;
	
}
