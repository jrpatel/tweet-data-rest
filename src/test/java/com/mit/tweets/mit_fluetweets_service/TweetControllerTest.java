package com.mit.tweets.mit_fluetweets_service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.ResourceAccessException;
import com.mit.tweets.service.TweetService;
import com.mit.tweets.controlller.TweetController;
import com.mit.tweets.exception.ResponseErrorHandler;
import com.mit.tweets.exception.TweetException;
import com.mit.tweets.model.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
 * Test Class to test basic functionality  of 
 * TweetController using Mockito for mocking
 * a TweetService Class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TweetControllerTest {
	private MockMvc mocMvc;
	@Mock
	private TweetService tweetServiceMock;
	@Autowired
	@InjectMocks
	private TweetController controller;
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			
			MediaType.APPLICATION_JSON.getSubtype(),                      
			    Charset.forName("utf8")                   
			  );

	/*
	 * this method is used to set up resources for running test cases.
	 */
	@Before
	public void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		this.mocMvc = MockMvcBuilders.standaloneSetup(controller)
					  .setControllerAdvice(new ResponseErrorHandler())
					  .build();
	}
	
	/*
	 * verifies when everything goes well return status
	 * would be 200.
	 */
	@Test
	public void getTweetsTestFound() throws Exception{
		Tweet[] tweets = setTweetsData();
		Tweet tweet = tweets[0];
		List<Response> responseList = new ArrayList<Response>();
		Response response = new Response();
		response.setUser_name(tweet.getUser_name());
		response.setTweet_text(tweet.getTweet_text());
		response.setTweet_date(tweet.getTweet_date());
		response.setLatitude(tweet.getLatitude());
		response.setLongitude(tweet.getLongitude());
		response.setAvgMax(124.00);
		response.setAvgMin(135.00);
		responseList.add(response);
		when(tweetServiceMock.getTweets()).thenReturn(tweets);
		when(tweetServiceMock.getWeatherResponse(tweets)).thenReturn(responseList);
		mocMvc.perform(get("/api/tweets"))
			  .andExpect(status().isOk())
			  .andExpect(content().contentType(APPLICATION_JSON_UTF8));
	}
	
	/*
	 * this method verifies if no fever/flue tweets found it will
	 * throw a TweetException with internal server error.
	 */
	@Test
	public void getTweetsNotTestFound() throws Exception{
	
		when(tweetServiceMock.getTweets()).thenThrow(TweetException.class);
		mocMvc.perform(get("/api/tweets"))
			  .andExpect(status().isInternalServerError());
		verify(tweetServiceMock,times(1)).getTweets();
		verifyNoMoreInteractions(tweetServiceMock);
	}
	
	/*
	 * this method verifies if there is any error in getting weather details
	 * TweetException will be thrown.
	 */
	@Test
	public void getTweetsNoWeatherInfoFound() throws Exception{
		Tweet[] tweets = setTweetsData();
		Tweet tweet = tweets[0];
		tweet.setUser_name("xyz");
		tweet.setTweet_text("got fever and flue");
		tweet.setAggravation(0);
		tweet.setLatitude("1255");
		tweet.setLongitude("-564");
		tweet.setTweet_date("20180524");
		tweets[0] = tweet;
		when(tweetServiceMock.getTweets()).thenReturn(tweets);
		when(tweetServiceMock.getWeatherResponse(tweets)).thenThrow(TweetException.class);
		mocMvc.perform(get("/api/tweets"))
		  .andExpect(status().isInternalServerError());
		verify(tweetServiceMock,times(1)).getTweets();
		verify(tweetServiceMock,times(1)).getWeatherResponse(tweets);
	}
	/*
	 * this test case will test if any used API resource isn't
	 * available.
	 */
	@Test
	public void getTweetsResourceNotAvailablTest() throws Exception{
		
		when(tweetServiceMock.getTweets()).thenThrow(ResourceAccessException.class);
		mocMvc.perform(get("/api/tweets"))
		  .andExpect(status().isNotFound());	
	}
	
	private Tweet[] setTweetsData() {
		Tweet[] tweets = new Tweet[1];
		Tweet tweet = new Tweet();
		tweet.setUser_name("xyz");
		tweet.setTweet_text("got fever and flue");
		tweet.setAggravation(0);
		tweet.setLatitude("1255");
		tweet.setLongitude("-564");
		tweet.setTweet_date("20180524");
		tweets[0] = tweet;
		return tweets;
	}
}
