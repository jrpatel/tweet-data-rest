package com.mit.tweets.mit_fluetweets_service;

import java.util.List;

import org.springframework.web.client.RestTemplate;

public class ResponseGenerator {
	
	RestTemplate restTemplate;
	Response[] response;
	
	ResponseGenerator(RestTemplate restTemplate, Response[] response){
		this.restTemplate = restTemplate;
		this.response = response;
	}
	
	
	Tweet[] getTweets() {
		System.out.println("execution started!!!!!");
		long starttime = System.currentTimeMillis();
		Tweet[] tweets = restTemplate.getForObject("http://api.flutrack.org/?s=feverORflue", Tweet[].class);
		System.out.println("tweets"+tweets.length);
		System.out.println("-----> time took to get tweets "+(System.currentTimeMillis() - starttime));
		return tweets;
	
	}
	
	Response[] getWeatherResponse(Tweet[] tweets) {
		
		 for(int i=0;i<response.length;i++) {
				
				response[i] = getResponse(tweets[i]);
			}
		 return response;
	}
	

	private Response getResponse(Tweet tweet) {
		long stime = System.currentTimeMillis();
		Response res = new Response();
		WeatherResponse weather =getLocation(tweet.getLongitude(), tweet.getLatitude());
		System.out.println("--> time took for 1 response"+(System.currentTimeMillis()-stime));
		//System.out.println(weather.toString().getClass());
		//System.out.println(weather.getClass());
		long btime = System.currentTimeMillis();
		List<Consolitaded_Weather>  temp = weather.getConsolidated_weather();
		if(temp!=null)
			{res.setAvgMax( temp.stream().mapToDouble(Consolitaded_Weather::getMax_temp).average());
			res.setAvgMin(temp.stream().mapToDouble(Consolitaded_Weather::getMin_temp).average());
			res.setUser_name(tweet.getUser_name());
			res.setTweet_text(tweet.getTweet_text());
			res.setLatitude(tweet.getLatitude());
			res.setLongitude(tweet.getLongitude());
			res.setTweet_date(tweet.getTweet_date());
			res.setAggravation(tweet.getAggravation());
			}
		else {
			System.out.println("something wrong here???");
		}
		System.out.println("----> time took post processing response"+(System.currentTimeMillis()-btime));
		//System.out.println("here coming"+res.getAvgMax());
		return res;
	}
	
	private WeatherResponse  getLocation(String lonn, String latt) {
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
		}
}
