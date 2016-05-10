package com.pratilipi.api.impl.init.shared;

import com.pratilipi.api.shared.GenericRequest;

public class GetInitRequest extends GenericRequest {
	
	private Integer year;
	private Integer month;
	private Integer day;

	
	public Integer getYear() {
		return year;
	}
	
	public Integer getMonth() {
		return month;
	}

	public Integer getDay() {
		return day;
	}
	
}
