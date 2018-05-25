package com.mit.tweets.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.mit.tweets.exception.TweetException;
import com.mit.tweets.model.Consolitaded_Weather;
import com.mit.tweets.model.Response;
import com.mit.tweets.model.Tweet;
import com.mit.tweets.model.WeatherResponse;
import com.mit.tweets.model.Woeid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TweetServiceImpl implements TweetService {
	
	private static final Logger log = LoggerFactory.getLogger(TweetServiceImpl.class);
	RestTemplate restTemplate;
	List<Response> responses;
	Response response;
	
	public TweetServiceImpl(){
		this.restTemplate = new RestTemplate();
		this.responses = new ArrayList<Response>();
	}
	@Override
	public Tweet[] getTweets() {
		log.info("Getting Tweets");
		Tweet[] tweets = restTemplate.getForObject("http://api.flutrack.org/?s=feverORflue&&limit=10",Tweet[].class);
		return tweets;
	
	}
	@Override
	public List<Response> getWeatherResponse(Tweet[] tweets) {
		for(int i=0;i<tweets.length;i++) {
			 if(tweets[i]!=null) {
				responses.add(getResponse(tweets[i]));
			 }
			 else 
			 {
				 try {
					throw new TweetException("Found Null Tweet Object!");
				} catch (TweetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 }
				 log.info("Tweet Object!! its null");
			 
		 }
		 return responses;
	}
	@Override
    public Woeid getWhereonEarth(String lonn, String latt) {
		Woeid[] woeid = restTemplate.getForObject("https://www.metaweather.com/api/location/search/?lattlong="+latt+","+lonn, Woeid[].class);
		return woeid[0];
    		
    }
	@Override
    public  WeatherResponse getWeatherDetails(Woeid woeid) {
		WeatherResponse weather = restTemplate.getForObject("https://www.metaweather.com/api/location/"+woeid, WeatherResponse.class);
		return weather;
    }
	@Override
	public Response getResponse(Tweet tweet) {
		Woeid woeid = getWhereonEarth(tweet.getLongitude(), tweet.getLatitude());
		WeatherResponse weather = getWeatherDetails(woeid);
		List<Consolitaded_Weather>  temp = weather.getConsolidated_weather();
		response = new Response();
		if(temp!=null)
			{
			response.setAvgMax( temp.stream().mapToDouble(Consolitaded_Weather::getMax_temp).average());
			response.setAvgMin(temp.stream().mapToDouble(Consolitaded_Weather::getMin_temp).average());
			response.setUser_name(tweet.getUser_name());
			response.setTweet_text(tweet.getTweet_text());
			response.setLatitude(tweet.getLatitude());
			response.setLongitude(tweet.getLongitude());
			response.setTweet_date(tweet.getTweet_date());
			response.setAggravation(tweet.getAggravation());
			}
		return response;
	}
}
