package com.mit.tweets.mit_fluetweets_service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Woeid {
	
	private int woeid;
	
	Woeid(){
		
	}

	public int getWoeid() {
		return woeid;
	}

	public void setWoeid(int woeid) {
		this.woeid = woeid;
	}
	
	@Override
	public String toString() {
		return  woeid +"";
				
	}

}
