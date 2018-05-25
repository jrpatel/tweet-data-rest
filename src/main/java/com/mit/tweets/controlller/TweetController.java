package com.mit.tweets.controlller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import com.mit.tweets.exception.TweetException;
import com.mit.tweets.model.Response;
import com.mit.tweets.model.Tweet;
import com.mit.tweets.service.TweetService;
import com.mit.tweets.service.TweetServiceImpl;

@RestController
public class TweetController {
	private static final Logger log = LoggerFactory.getLogger(TweetServiceImpl.class);
	
	@Autowired
	TweetService service;
	
	@RequestMapping(value="/api/tweets",method = RequestMethod.GET)
	
	public @ResponseBody List<Response> getFlueTweets() throws TweetException{
		log.info("Getting Tweets");
	    Tweet[] tweets = service.getTweets();
		List<Response> response = service.getWeatherResponse(tweets);
		log.info("Returning response");
		return response;
		
	}

}
