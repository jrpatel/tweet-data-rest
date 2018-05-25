package com.mit.tweets.mit_fluetweets_service;

import org.assertj.core.internal.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import com.mit.tweets.service.TweetService;
import com.mit.tweets.exception.TweetException;
import com.mit.tweets.model.*;
import java.util.ArrayList;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;


import java.nio.charset.Charset;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration

/*
 * Test Class to test basic functionality  of 
 * TweetController using Mockito for mocking
 * a TweetService Class.
 */

public class TweetControllerTest {
	
	private MockMvc mocMvc;
	@Autowired 
	private TweetService tweetServiceMock;
	
	@Autowired
	private WebApplicationContext webContext;
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			
			MediaType.APPLICATION_JSON.getSubtype(),                      
			    Charset.forName("utf8")                   
			  );

	
	@Before
	public void setup() throws Exception{
		
		this.mocMvc = webAppContextSetup(webContext).build();
		
	}
	
	@Test
	public void getTweets_NotFound() throws Exception{
		
		when(tweetServiceMock.getTweets()).thenThrow(new TweetException("Internal Server Error"));
		mocMvc.perform(get("/api/tweets"))
			  .andExpect(status().isOk());
		verify(tweetServiceMock,times(1)).getTweets();
		
		
	}
	
	@Test
	public void getTweetsTestFound() throws Exception{
		
		Tweet[] tweets = new Tweet[1];
		Tweet tweet = new Tweet();
		tweet.setUser_name("xyz");
		tweet.setTweet_text("got fever and flue");
		tweet.setAggravation(0);
		tweet.setLatitude("1255");
		tweet.setLongitude("-564");
		tweet.setTweet_date("20180524");
		tweets[0] = tweet;
		when(tweetServiceMock.getTweets()).thenReturn(tweets);
		System.out.println("what ");
		mocMvc.perform(get("/api/tweets"))
			  .andExpect(status().isOk())
			  .andExpect(content().contentType(APPLICATION_JSON_UTF8));
			  
		
		
	}
	

}
