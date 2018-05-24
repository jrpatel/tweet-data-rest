package com.mit.tweets.mit_fluetweets_service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TweetService {
	
	RestTemplate restTemplate;
	
	Response[] responses;
	
	Response response;
	
	TweetService(){
		this.restTemplate = new RestTemplate();
		this.responses = new Response[10];
		//this.response = new Response();
	}
	 
	
	Tweet[] getTweets() {
		System.out.println("execution started!!!!!");
		long starttime = System.currentTimeMillis();
		Tweet[] tweets = restTemplate.getForObject("http://api.flutrack.org/?s=feverORflue&&limit=10", Tweet[].class);
		System.out.println("tweets"+tweets.length);
		System.out.println("-----> time took to get tweets "+(System.currentTimeMillis() - starttime));
		return tweets;
	
	}
	
	Response[] getWeatherResponse(Tweet[] tweets) {
		
		 for(int i=0;i<responses.length;i++) {
				
				responses[i] = getResponse(tweets[i]);
			}
		 return responses;
	}
	
    public Woeid getWhereonEarth(String lonn, String latt) {
    	
    		long starttime = System.currentTimeMillis();
		Woeid[] woeid = restTemplate.getForObject("https://www.metaweather.com/api/location/search/?lattlong="+latt+","+lonn, Woeid[].class);
		System.out.println("----> took to find location"+(System.currentTimeMillis()-starttime));
		return woeid[0];
    		
    }
    
    public  WeatherResponse getWeatherDetails(Woeid woeid) {
    		try {
    			long s2 = System.currentTimeMillis();
			WeatherResponse weather = restTemplate.getForObject("https://www.metaweather.com/api/location/"+woeid, WeatherResponse.class);
			System.out.println("----> took time to get the weather details"+(System.currentTimeMillis()-s2));
			System.out.println(weather);
			//System.out.println("---> total time took "+(System.currentTimeMillis()-starttime));
			return weather;
			}
			catch(Exception e) {
				System.out.println("here in catch from catchhhhhh!!!");
				System.out.println(e.getMessage());
				return null;
			}
    	
    }
    
	public Response getResponse(Tweet tweet) {
		long stime = System.currentTimeMillis();
		
		Woeid woied = getWhereonEarth(tweet.getLongitude(), tweet.getLatitude());
		WeatherResponse weather = getWeatherDetails(woied);
		
		//Response res = new Response();
		//WeatherResponse weather =getLocation(tweet.getLongitude(), tweet.getLatitude());
		System.out.println("--> time took for 1 response"+(System.currentTimeMillis()-stime));
		//System.out.println(weather.toString().getClass());
		//System.out.println(weather.getClass());
		long btime = System.currentTimeMillis();
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
		else {
			System.out.println("something wrong here???");
		}
		System.out.println("----> time took post processing response"+(System.currentTimeMillis()-btime));
		//System.out.println("here coming"+res.getAvgMax());
		return response;
	}
	
	/*private WeatherResponse  getLocation(String lonn, String latt) {
		//RestTemplate restTemplate = new RestTemplate();
			long starttime = System.currentTimeMillis();
			Woeid[] woeid = restTemplate.getForObject("https://www.metaweather.com/api/location/search/?lattlong="+latt+","+lonn, Woeid[].class);
			System.out.println("----> took to find location"+(System.currentTimeMillis()-starttime));
			System.out.println("here longitude is"+lonn+"lattitude is"+latt);
			System.out.println("may be retrived is"+woeid[0]);
			System.out.println("lets see how many woid it gives"+woeid.length);
			
			for(int i=0;i<woeid.length;i++) {
				System.out.println("retrieved woeid is"+woeid[i]);
			}
			try {
			long s2 = System.currentTimeMillis();
			WeatherResponse weather = restTemplate.getForObject("https://www.metaweather.com/api/location/"+woeid[0], WeatherResponse.class);
			System.out.println("----> took time to get the weather details"+(System.currentTimeMillis()-s2));
			System.out.println(weather);
			System.out.println("---> total time took "+(System.currentTimeMillis()-starttime));
			return weather;
			}
			catch(Exception e) {
				System.out.println("here in catch from catchhhhhh!!!");
				System.out.println(e.getMessage());
				return null;
			}
			//return null;
		}*/
}
