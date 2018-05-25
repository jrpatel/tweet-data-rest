package com.mit.tweets.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
	
	private List<Consolitaded_Weather> consolidated_weather;
	
	WeatherResponse(){
		
	}

	public List<Consolitaded_Weather> getConsolidated_weather() {
		return consolidated_weather;
	}

	public void setConsolidated_weather(List<Consolitaded_Weather> consolidated_weather) {
		this.consolidated_weather = consolidated_weather;
	}
	
	@Override
	public String toString() {
		return "consolidated_weather :" +getConsolidated_weather();
		
	}
	

}
