package com.pratilipi.api.init.shared;

import java.util.Map;

import com.pratilipi.api.shared.GenericRequest;

public class InitApiRequest extends GenericRequest {

	private String targetURL;
	
	private String method;
	
	private Map<String,String> URLParameters;
	
	public String getTargetURL() {
		return targetURL;
	}
	
	public String getMethod() {
		return method;
	}
	
	public Map<String,String> getURLParameters() {
		return URLParameters;
	}
	
}
