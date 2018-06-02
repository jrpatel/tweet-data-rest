package com.mit.tweets.service;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

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

/**
 * 
 * @author jinal
 * implements TweetService interface to access other REST API's using RestTemplate.
 *
 */
@Service
public class TweetServiceImpl implements TweetService {
	
	private static final Logger log = LoggerFactory.getLogger(TweetServiceImpl.class);
	private static TweetServiceImpl _instance; 
	RestTemplate restTemplate;
	private List<Response> responses;
	private Response response;
	
	public TweetServiceImpl(){
		this.restTemplate = new RestTemplate();
		this.responses = new ArrayList<Response>();
	}
	/*
	 * @see com.mit.tweets.service.TweetService#getTweets()
	 * this method will act as a client using RestTemplate and get 10 flueOrfever tweets in Array.
	 * Jackson api is being used for deserailzing Response into Tweet POJO
	 * 
	 */
	@Override
	public Tweet[] getTweets() throws TweetException {
		log.info("Getting Tweets in Service api");
		Tweet[] tweets = restTemplate.getForObject("http://api.flutrack.org/?s=feverORflue&&limit=10",Tweet[].class);
		if(tweets.length==0)
			{
			throw new TweetException("No Flue/Fever tweets!!!");
			}
		return tweets;
	
	}
	/*
	 * @see com.mit.tweets.service.TweetService#getWeatherResponse(com.mit.tweets.model.Tweet[])
	 * Using Tweet User data, this method will find weather deatials and return the Response as a 
	 * List.
	 */
	@Override
	public List<Response> getWeatherResponse(Tweet[] tweets) throws TweetException {
		for(int i=0;i<tweets.length;i++) {
			 if(tweets[i]!=null) {
				responses.add(getResponse(tweets[i]));
			 }
			 else 
			 {
				 log.info("Tweet Object!! its null");
			 }
		 }
		 return responses;
	}

	/* 
	 * @see com.mit.tweets.service.TweetService#getWhereonEarth(java.lang.String, java.lang.String)
	 * using RestTemplate and Longitude and Latitude, this method will get UserLocation and return 
	 * where on earth id to find weather details..
	 */
	@Override
    public Woeid getWhereonEarth(String lonn, String latt) {
		Woeid[] woeid = restTemplate.getForObject("https://www.metaweather.com/api/location/search/?lattlong="+latt+","+lonn, Woeid[].class);
		return woeid[0];
    		
    }
	
	/*
	 * @see com.mit.tweets.service.TweetService#getWeatherDetails(com.mit.tweets.model.Woeid)
	 * using RestTemplate and WOEID, it will get the next five days weather forecast at particular
	 * location.
	 */
	@Override
    public  WeatherResponse getWeatherDetails(Woeid woeid) {
		WeatherResponse weather = restTemplate.getForObject("https://www.metaweather.com/api/location/"+woeid, WeatherResponse.class);
		return weather;
    }
	/*
	 * @see com.mit.tweets.service.TweetService#getResponse(com.mit.tweets.model.Tweet)
	 * this method will get required details and generate the response.
	 */
	@Override
	public Response getResponse(Tweet tweet) throws TweetException{
		Woeid woeid = getWhereonEarth(tweet.getLongitude(), tweet.getLatitude());
		WeatherResponse weather = getWeatherDetails(woeid);
		List<Consolitaded_Weather>  temp = weather.getConsolidated_weather();
		response = new Response();
		if(!temp.isEmpty())
			{
			/*
			 * using temp List which contains weather details of next five days, it calcualtes average min 
			 * and max.
			 */
			OptionalDouble avgMax = temp.stream().mapToDouble(Consolitaded_Weather::getMax_temp).average();
			response.setAvgMax(avgMax.getAsDouble());
			OptionalDouble avgMin = temp.stream().mapToDouble(Consolitaded_Weather::getMin_temp).average();
			response.setAvgMin(avgMin.getAsDouble());
			}
	    else 
			throw new TweetException("Weather Details not available!!");
			
		response.setUser_name(tweet.getUser_name());
		response.setTweet_text(tweet.getTweet_text());
		response.setLatitude(tweet.getLatitude());
		response.setLongitude(tweet.getLongitude());
		response.setTweet_date(tweet.getTweet_date());
		response.setAggravation(tweet.getAggravation());
		return response;
	}
	
	public static TweetService getServiceInstance() {
		
		if(_instance == null) {
			
			_instance = new TweetServiceImpl();
		}
		return _instance;
	}
}
