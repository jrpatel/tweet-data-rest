package com.mit.tweets.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * this class will be used to deserializing Weather 
 * response.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Consolitaded_Weather {
	
	private Double min_temp;
	private Double max_temp;
	
	Consolitaded_Weather(){
		
	}

	public Double getMin_temp() {
		return min_temp;
	}

	public void setMin_temp(Double min_temp) {
		this.min_temp = min_temp;
	}

	public Double getMax_temp() {
		return max_temp;
	}

	public void setMax_temp(Double max_temp) {
		this.max_temp = max_temp;
	}
	
	@Override
	public String toString() {
		return "min_temp" + min_temp +
				"max_temp" + max_temp;
	}
	
}
