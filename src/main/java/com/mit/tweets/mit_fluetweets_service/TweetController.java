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
	
	//RestTemplate restTemplate = new RestTemplate();
	ResponseGenerator service;
	
	TweetController(ResponseGenerator service){
		
		this.service = service;
		
	}
	
	
	@RequestMapping("/tweets")
	public Response[] getFlueTweets() {
		
		System.out.println("execution started!!!!!");
		long starttime = System.currentTimeMillis();
		Tweet[] tweets = service.getTweets();
	//	Tweet[] tweets = restTemplate.getForObject("http://api.flutrack.org/?s=feverORflue", Tweet[].class);
		System.out.println("tweets"+tweets.length);
		System.out.println("-----> time took to get tweets "+(System.currentTimeMillis() - starttime));
		Response[] response = service.getWeatherResponse(tweets);
		return response;
		/*System.out.println("starting to prepare the response");
		long starttime2 = System.currentTimeMillis();
        for(int i=0;i<response.length;i++) {
			
			response[i] = getResponse(tweets[i]);
		}
        System.out.println("-----> processing response took time"+(System.currentTimeMillis()-starttime2));
        System.out.println("----> total time "+(System.currentTimeMillis()-starttime));*/
	//	return response;
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
		
	}*/
	

}
