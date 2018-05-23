package com.mit.tweets.mit_fluetweets_service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sun.javafx.collections.MappingChange.Map;
@RestController
public class TweetController {
	
	@RequestMapping("/tweets")
	public Response[] getTweets() {
		
		RestTemplate restTemplate = new RestTemplate();
		Tweet[] tweets = restTemplate.getForObject("http://api.flutrack.org/?limit=10", Tweet[].class);
		WeatherResponse weather =getLocation(tweets[0].getLongitude(), tweets[0].getLongitude());
		System.out.println(weather.toString().getClass());
		System.out.println(weather.getClass());
		Response[] response = new Response[tweets.length];  
        for(int i=0;i<response.length;i++) {
			
			response[i] = getResponse(tweets[i]);
		}
		return response;
		/////Threading ///////
		/*ExecutorService executor = Executors.newFixedThreadPool(2);
		List<FutureTask<Response[]>> taskList = new ArrayList<FutureTask<Response[]>>();
	
		FutureTask<Response[]> futuretask1 = new FutureTask<Response[]>(new Callable<Response[]>(){

			@Override
			public Response[] call() throws Exception {
				// TODO Auto-generated method stub
				Response[] res = new Response[5];
				for(int i=0;i<5;i++) {
					
					res[i] = getResponse(tweets[i]);
					wait(20000);
				}
				return res;
			}
			
		});
		taskList.add(futuretask1);
		executor.execute(futuretask1);
		FutureTask<Response[]> futuretask2 = new FutureTask<Response[]>(new Callable<Response[]>(){

			@Override
			public Response[] call() throws Exception {
				// TODO Auto-generated method stub
				Response[] res = new Response[5];
				for(int i=5;i<response.length;i++) {
					res[i] = getResponse(tweets[i]);
					wait(20000);
				}
				return res;
			}
			
		});
		taskList.add(futuretask2);
		executor.execute(futuretask2);
		Response[] newRes = new Response[10];
		for(int i=0;i<2;i++) {
			System.out.println("lets see");
			FutureTask<Response[]> futuretask = taskList.get(i);
			Response[] temp;
			try {
				 temp = futuretask.get();
				 System.out.println("temp size"+temp.length);
				 for(int j=0;j<5;j++) {
						newRes[j+(i*5)] = temp[j];
					}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//this.wait();
		executor.shutdown();
		return newRes;*/
	/*	for(int i=0;i<5;i++) {
			
			response[i] = getResponse(tweets[i]);
		}
		for(int i=5;i<10;i++) {
			response[i] = getResponse(tweets[i]);
		}*/
		
	}
	
	
	private WeatherResponse  getLocation(String lonn, String latt) {
		RestTemplate restTemplate = new RestTemplate();
		Woeid[] woeid = restTemplate.getForObject("https://www.metaweather.com/api/location/search/?lattlong="+latt+","+lonn, Woeid[].class);
		System.out.println("may be retrived is"+woeid[0]);
		try {
		WeatherResponse weather = restTemplate.getForObject("https://www.metaweather.com/api/location/"+woeid[0], WeatherResponse.class);
		System.out.println(weather);
		return weather;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	private Response getResponse(Tweet tweet) {
		Response res = new Response();
		WeatherResponse weather =getLocation(tweet.getLongitude(), tweet.getLongitude());
		System.out.println(weather.toString().getClass());
		System.out.println(weather.getClass());
		List<Consolitaded_Weather>  temp = weather.getConsolidated_weather();
		res.setAvgMax( temp.stream().mapToDouble(Consolitaded_Weather::getMax_temp).average());
		res.setAvgMin(temp.stream().mapToDouble(Consolitaded_Weather::getMax_temp).average());
		res.setUser_name(tweet.getUser_name());
		res.setTweet_text(tweet.getTweet_text());
		res.setLatitude(tweet.getLatitude());
		res.setLongitude(tweet.getLongitude());
		res.setTweet_date(tweet.getTweet_date());
		res.setAggravation(tweet.getAggravation());
		System.out.println("here coming"+res.getAvgMax());
		return res;
		
	}
	

}
