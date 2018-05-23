package com.mit.tweets.mit_fluetweets_service;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tweet {
	
	private String user_name;
	private String tweet_text;
	private String latitude;
	private String longitude;
	private String tweet_date;
	private String aggravation;
	//private String avgMax;
	//private String avgMin;
	
	public Tweet() {
		
	}
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getTweet_text() {
		return tweet_text;
	}
	public void setTweet_text(String tweet_text) {
		this.tweet_text = tweet_text;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getTweet_date() {
		return tweet_date;
	}
	public void setTweet_date(String tweet_date) {
		this.tweet_date = tweet_date;
	}
	public String getAggravation() {
		return aggravation;
	}
	public void setAggravation(String aggravation) {
		this.aggravation = aggravation;
	}
	/*public String getAvgMax() {
		return avgMax;
	}
	public void setAvgMax(String avgMax) {
		this.avgMax = avgMax;
	}
	public String getAvgMin() {
		return avgMin;
	}
	public void setAvgMin(String avgMin) {
		this.avgMin = avgMin;
	} */
	
	@Override
	public String toString() {
		return "[{" +
				"user_name:" +user_name +
				"tweet_text" +tweet_text +
				"latitude" +latitude + 
				"longitude" +longitude +
				"tweet_date" +tweet_date +
				"aggravation" +aggravation +
				"}]";	
	}
	

}
