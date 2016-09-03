package com.pratilipi.data.client;

import com.google.gson.JsonObject;

public class InitBannerData {

	private String bannerId;

	private String title;
	
	private String imageUrl;
	
	private String actionUrl;
	
	private String apiName;
	
	private Object apiRequest;
	
	
	public InitBannerData( String bannerId ) {
		this.bannerId = bannerId;
	}


	public String getId() {
		return bannerId;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle( String title ) {
		this.title = title;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl( String imageUrl ) {
		this.imageUrl = imageUrl;
	}
	
	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl( String actionUrl ) {
		this.actionUrl = actionUrl;
	}

	public String getApiName() {
		return apiName;
	}
	
	public void setApiName( String apiName ) {
		this.apiName = apiName;
	}
	
	public Object getApiRequest() {
		return apiRequest;
	}
	
	public void setApiRequest( String apiRequest ) {
		this.apiRequest = apiRequest;
	}
	
	public void setApiRequest( JsonObject apiRequest ) {
		this.apiRequest = apiRequest;
	}

}